package com.tup.ps.erpevents.services.impl;


import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.relations.EventsGuestsDTO;
import com.tup.ps.erpevents.dtos.guest.GuestAccessDTO;
import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import com.tup.ps.erpevents.dtos.notification.KeyValueCustomPair;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.GuestEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsGuestsEntity;
import com.tup.ps.erpevents.enums.AccessType;
import com.tup.ps.erpevents.repositories.EventRepository;
import com.tup.ps.erpevents.repositories.EventsGuestsRepository;
import com.tup.ps.erpevents.repositories.GuestRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.EventService;
import com.tup.ps.erpevents.services.GuestService;
import com.tup.ps.erpevents.services.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private static final String[] GUEST_FIELDS = {
            "firstName", "lastName", "email", "note"
    };
    @Autowired
    private GuestRepository guestRepository;
    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenericSpecification<GuestEntity> specification;
    @Autowired
    private EventsGuestsRepository eventsGuestsRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private NotificationService notificationService;

    @Override
    public Page<GuestDTO> findAll(Pageable pageable) {
        return guestRepository.findAllBySoftDelete(false, pageable)
                .map(guest -> {
                    GuestDTO dto = modelMapper.map(guest, GuestDTO.class);

                    if (guest.getGuestEvents() != null && !guest.getGuestEvents().isEmpty()) {
                        var firstEvent = guest.getGuestEvents().get(0);
                        dto.setNote(firstEvent.getNote());
                        dto.setType(firstEvent.getType());
                    }

                    return dto;
                });
    }

    @Override
    public Optional<GuestDTO> findById(Long id) {
        return guestRepository.findById(id)
                .filter(guest -> Boolean.FALSE.equals(guest.getSoftDelete()))
                .map(guest -> {
                    GuestDTO dto = modelMapper.map(guest, GuestDTO.class);

                    if (guest.getGuestEvents() != null && !guest.getGuestEvents().isEmpty()) {
                        var firstEvent = guest.getGuestEvents().get(0);
                        dto.setNote(firstEvent.getNote());
                        dto.setType(firstEvent.getType());
                    }

                    return dto;
                });
    }

    @Override
    @Transactional
    public GuestDTO save(GuestPostDTO dto) {
        EventEntity event = eventRepository.findById(dto.getIdEvent())
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        Optional<GuestEntity> guestOpt = guestRepository.findByFirstNameAndLastNameAndEmail(
                dto.getFirstName(), dto.getLastName(), dto.getEmail());

        GuestEntity guest;
        if (guestOpt.isPresent()) {
            guest = guestOpt.get();
        } else {
            guest = modelMapper.map(dto, GuestEntity.class);
            guest.setSoftDelete(false);
            guest = guestRepository.save(guest);
        }

        Optional<EventsGuestsEntity> relationOpt = eventsGuestsRepository
                .findByEventAndGuest(event, guest);

        EventsGuestsEntity relation = relationOpt.orElseGet(EventsGuestsEntity::new);
        relation.setEvent(event);
        relation.setGuest(guest);
        relation.setType(dto.getType());
        relation.setNote(dto.getNote());
        relation.setSector(dto.getSector());
        relation.setRowTable(dto.getRowTable());
        relation.setSeat(dto.getSeat());

        eventsGuestsRepository.save(relation);

        sendGuestNotification(event, guest);

        return modelMapper.map(guest, GuestDTO.class);
    }

    @Override
    public GuestDTO update(Long idGuest, GuestPutDTO dto) {
        GuestEntity guest = guestRepository.findById(idGuest)
                .orElseThrow(() -> new EntityNotFoundException("Invitado no encontrado"));

        EventEntity event = eventRepository.findById(dto.getIdEvent())
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        EventsGuestsEntity relation = guest.getGuestEvents().stream()
                .filter(rel -> rel.getEvent().getIdEvent().equals(event.getIdEvent()))
                .toList().get(0);

        relation.setType(dto.getType());
        relation.setNote(dto.getNote());
        relation.setSector(dto.getSector());
        relation.setRowTable(dto.getRowTable());
        relation.setSeat(dto.getSeat());

        eventsGuestsRepository.save(relation);

        modelMapper.map(dto, guest);

        return modelMapper.map(guestRepository.save(guest), GuestDTO.class);
    }

    @Override
    public void delete(Long id) {
        GuestEntity guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invitado no encontrado"));
        guest.setSoftDelete(true);
        guestRepository.save(guest);
    }

    @Override
    public Page<GuestDTO> findByFilters(Pageable pageable,
                                        String guestType,
                                        Boolean isActive,
                                        String searchValue,
                                        LocalDate creationStart,
                                        LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (guestType != null) {
            filters.put("type", guestType);
        }
        if (isActive != null) {
            filters.put("softDelete", !isActive);
        }

        Specification<GuestEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<GuestEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, GUEST_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<GuestEntity> dateSpec = specification.filterBetween(creationStart, creationEnd, "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return guestRepository.findAll(spec, pageable)
                .map(guest -> modelMapper.map(guest, GuestDTO.class));
    }

    @Override
    public List<GuestDTO> saveGuestsToEvent(List<GuestPostDTO> guestPostDTOList, Long idEvent) {
        EventEntity event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        List<GuestEntity> savedGuests = new ArrayList<>();
        List<EventsGuestsEntity> guestRelations = new ArrayList<>();

        for (GuestPostDTO guestDTO : guestPostDTOList) {
            GuestEntity guestEntity = guestRepository
                    .findByFirstNameAndLastNameAndEmail(guestDTO.getFirstName(),
                            guestDTO.getLastName(), guestDTO.getEmail())
                    .orElseGet(() -> {
                        GuestEntity newGuest = modelMapper.map(guestDTO, GuestEntity.class);
                        return guestRepository.save(newGuest);
                    });

            EventsGuestsEntity relation = new EventsGuestsEntity();
            relation.setEvent(event);
            relation.setGuest(guestEntity);
            relation.setType(guestDTO.getType());
            relation.setNote(guestDTO.getNote());
            relation.setSector(guestDTO.getSector());
            relation.setRowTable(guestDTO.getRowTable());
            relation.setSeat(guestDTO.getSeat());
            guestRelations.add(relation);

            savedGuests.add(guestEntity);

            sendGuestNotification(event, guestEntity);
        }

        eventsGuestsRepository.saveAll(guestRelations);

        return savedGuests.stream().map(guest -> modelMapper.map(guest, GuestDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<GuestDTO> getGuestFromEvent(Long idEvent) {
        EventEntity event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        return event.getEventGuests().stream()
                .map(relation -> {
                    GuestDTO dto = modelMapper.map(relation.getGuest(), GuestDTO.class);
                    dto.setType(relation.getType());
                    dto.setNote(relation.getNote());
                    dto.setAcreditation(modelMapper.map(relation, EventsGuestsDTO.class));
                    return dto;
                }).toList();
    }

    @Override
    public GuestDTO registerAccess(GuestAccessDTO dto) {
        GuestEntity guest = guestRepository.findById(dto.getIdGuest())
                .orElseThrow(() -> new EntityNotFoundException("Invitado no encontrado"));

        EventEntity event = eventRepository.findById(dto.getIdEvent())
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        EventsGuestsEntity relation = eventsGuestsRepository
                .findByEventAndGuest(event, guest)
                .orElseThrow(() -> new EntityNotFoundException("Relación entre invitado y evento no encontrada"));

        relation.setAccessType(dto.getAccessType());
        relation.setActionDate(dto.getActionDate());

        EventsGuestsEntity savedRelation = eventsGuestsRepository.save(relation);
        GuestDTO guestDTO = modelMapper.map(guest, GuestDTO.class);
        guestDTO.setType(relation.getType());
        guestDTO.setNote(relation.getNote());
        guestDTO.setAcreditation(modelMapper.map(savedRelation, EventsGuestsDTO.class));

        return guestDTO;
    }

    private List<Long> extractGuestIds(List<EventsGuestsEntity> eventGuests) {
        if (eventGuests == null) {
            return List.of();
        }
        return eventGuests.stream()
                .map(rel -> rel.getGuest().getIdGuest())
                .toList();
    }

    private List<EventsGuestsDTO> mapEventGuests(List<EventsGuestsEntity> eventGuests) {
        if (eventGuests == null) {
            return List.of();
        }
        return eventGuests.stream()
                .map(rel -> {
                    EventsGuestsDTO guestsDTO = modelMapper.map(rel, EventsGuestsDTO.class);
                    guestsDTO.setIdGuest(rel.getGuest().getIdGuest());
                    guestsDTO.setIdEvent(rel.getEvent().getIdEvent());
                    return guestsDTO;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void sendGuestNotification(EventEntity event, GuestEntity guest) {
        NotificationPostDTO notificationPostDTO = new NotificationPostDTO();
        notificationPostDTO.setIdTemplate(6L); // Guest Template
        notificationPostDTO.setSubject("Estás invitado a " + event.getTitle());

        notificationPostDTO.setVariables(List.of(
                new KeyValueCustomPair("NAME", guest.getFirstName()),
                new KeyValueCustomPair("LAST_NAME", guest.getLastName()),
                new KeyValueCustomPair("EVENT", event.getTitle()),
                new KeyValueCustomPair("DATE", event.getStartDate().toString()),
                new KeyValueCustomPair("PLACE", event.getLocation().getFantasyName())
        ));

        notificationService.sendEmailToGuest(notificationPostDTO, guest);
    }



}
