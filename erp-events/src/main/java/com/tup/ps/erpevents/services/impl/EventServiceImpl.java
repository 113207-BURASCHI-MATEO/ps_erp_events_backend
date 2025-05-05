package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.EventService;
import jakarta.persistence.EntityNotFoundException;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private static final String[] EVENT_FIELDS = {
            "title", "description"
    };

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenericSpecification<EventEntity> specification;

    //@Override
    /*public Page<EventDTO> findAll(Pageable pageable) {
        return eventRepository.findAllBySoftDelete(false, pageable)
                .map(event -> modelMapper.map(event, EventDTO.class));
    }*/
    @Override
    public Page<EventDTO> findAll(Pageable pageable) {
        Page<EventEntity> page = eventRepository.findAllBySoftDelete(false, pageable);

        return page.map(event -> {
            //EventDTO dto = modelMapper.map(event, EventDTO.class);
            EventDTO dto = new EventDTO();
            dto.setIdEvent(event.getIdEvent());
            dto.setTitle(event.getTitle());
            dto.setDescription(event.getDescription());
            dto.setEventType(event.getEventType());
            dto.setStartDate(event.getStartDate());
            dto.setEndDate(event.getEndDate());
            dto.setStatus(event.getStatus());
            dto.setSoftDelete(event.getSoftDelete());
            dto.setClient(modelMapper.map(event.getClient(), ClientDTO.class));
            //dto.setLocation(modelMapper.map(event.getLocation(), LocationDTO.class));
            dto.setCreationDate(event.getCreationDate());
            dto.setUpdateDate(event.getUpdateDate());

            dto.setEmployees(event.getEmployees() != null
                    ? event.getEmployees().stream().map(EmployeeEntity::getIdEmployee).toList()
                    : List.of());

            dto.setGuests(event.getGuests() != null
                    ? event.getGuests().stream().map(GuestEntity::getIdGuest).toList()
                    : List.of());

            dto.setSuppliers(event.getSuppliers() != null
                    ? event.getSuppliers().stream().map(SupplierEntity::getIdSupplier).toList()
                    : List.of());

            dto.setTasks(event.getTasks() != null
                    ? event.getTasks().stream()
                    .map(task -> modelMapper.map(task, TaskDTO.class))
                    .toList()
                    : List.of());

            return dto;
        });
    }



    @Override
    public Optional<EventDTO> findById(Long id) {
        return eventRepository.findByIdEventAndSoftDeleteFalse(id)
                .filter(event -> Boolean.FALSE.equals(event.getSoftDelete()))
                .map(event -> {
                    EventDTO dto = new EventDTO();
                    dto.setIdEvent(event.getIdEvent());
                    dto.setTitle(event.getTitle());
                    dto.setDescription(event.getDescription());
                    dto.setEventType(event.getEventType());
                    dto.setStartDate(event.getStartDate());
                    dto.setEndDate(event.getEndDate());
                    dto.setStatus(event.getStatus());
                    dto.setSoftDelete(event.getSoftDelete());
                    dto.setClient(modelMapper.map(event.getClient(), ClientDTO.class));
                    //dto.setLocation(modelMapper.map(event.getLocation(), LocationDTO.class));
                    dto.setCreationDate(event.getCreationDate());
                    dto.setUpdateDate(event.getUpdateDate());

                    dto.setEmployees(event.getEmployees() != null
                            ? event.getEmployees().stream().map(EmployeeEntity::getIdEmployee).toList()
                            : List.of());

                    dto.setGuests(event.getGuests() != null
                            ? event.getGuests().stream().map(GuestEntity::getIdGuest).toList()
                            : List.of());

                    dto.setSuppliers(event.getSuppliers() != null
                            ? event.getSuppliers().stream().map(SupplierEntity::getIdSupplier).toList()
                            : List.of());

                    dto.setTasks(event.getTasks() != null
                            ? event.getTasks().stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList()
                            : List.of());

                    return dto;
                });
    }


    @Override
    public EventDTO save(EventPostDTO dto) {

        //EventEntity event = modelMapper.map(dto, EventEntity.class);
        EventEntity event = new EventEntity();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventType(dto.getEventType());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStatus(dto.getStatus());
        event.setCreationDate(LocalDateTime.now());
        event.setUpdateDate(LocalDateTime.now());
        event.setSoftDelete(false);

        event.setEmployees(getEmployeesFromIds(dto.getEmployeeIds()));
        event.setSuppliers(getSuppliersFromIds(dto.getSupplierIds()));
        event.setGuests(getGuestsFromIds(dto.getGuestIds()));

        ClientEntity client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        event.setClient(client);

        /*LocationEntity location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
        event.setLocation(location);*/

        EventEntity saved = eventRepository.save(event);

        EventDTO response = new EventDTO();
        response.setIdEvent(saved.getIdEvent());
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setEventType(saved.getEventType());
        response.setStartDate(saved.getStartDate());
        response.setEndDate(saved.getEndDate());
        response.setStatus(saved.getStatus());
        response.setSoftDelete(saved.getSoftDelete());
        response.setClient(modelMapper.map(saved.getClient(), ClientDTO.class));
        //response.setLocation(modelMapper.map(saved.getLocation(), LocationDTO.class));
        response.setCreationDate(saved.getCreationDate());
        response.setUpdateDate(saved.getUpdateDate());

        response.setEmployees(saved.getEmployees() != null
                ? saved.getEmployees().stream().map(EmployeeEntity::getIdEmployee).toList()
                : List.of());

        response.setGuests(saved.getGuests() != null
                ? saved.getGuests().stream().map(GuestEntity::getIdGuest).toList()
                : List.of());

        response.setSuppliers(saved.getSuppliers() != null
                ? saved.getSuppliers().stream().map(SupplierEntity::getIdSupplier).toList()
                : List.of());

        response.setTasks(saved.getTasks() != null
                ? saved.getTasks().stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList()
                : List.of());

        return response;
    }


    @Override
    public EventDTO update(Long id, EventPutDTO dto) {

        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventType(dto.getEventType());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStatus(dto.getStatus());
        event.setUpdateDate(LocalDateTime.now());

        event.setEmployees(getEmployeesFromIds(dto.getEmployeeIds()));
        event.setSuppliers(getSuppliersFromIds(dto.getSupplierIds()));
        event.setGuests(getGuestsFromIds(dto.getGuestIds()));

        /*if (dto.getIdClient() != null) {
            ClientEntity client = clientRepository.findById(dto.getIdClient())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
            event.setClient(client);
        }*/

    /*if (dto.getLocationId() != null) {
        LocationEntity location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
        event.setLocation(location);
    }*/

        EventEntity saved = eventRepository.save(event);

        EventDTO response = new EventDTO();
        response.setIdEvent(saved.getIdEvent());
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setEventType(saved.getEventType());
        response.setStartDate(saved.getStartDate());
        response.setEndDate(saved.getEndDate());
        response.setStatus(saved.getStatus());
        response.setSoftDelete(saved.getSoftDelete());
        response.setClient(modelMapper.map(saved.getClient(), ClientDTO.class));
        // response.setLocation(modelMapper.map(saved.getLocation(), LocationDTO.class));
        response.setCreationDate(saved.getCreationDate());
        response.setUpdateDate(saved.getUpdateDate());

        response.setEmployees(saved.getEmployees() != null
                ? saved.getEmployees().stream().map(EmployeeEntity::getIdEmployee).toList()
                : List.of());

        response.setGuests(saved.getGuests() != null
                ? saved.getGuests().stream().map(GuestEntity::getIdGuest).toList()
                : List.of());

        response.setSuppliers(saved.getSuppliers() != null
                ? saved.getSuppliers().stream().map(SupplierEntity::getIdSupplier).toList()
                : List.of());

        response.setTasks(saved.getTasks() != null
                ? saved.getTasks().stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList()
                : List.of());

        return response;
    }


    @Override
    public void delete(Long id) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        event.setSoftDelete(true);
        event.setUpdateDate(LocalDateTime.now());

        if (event.getTasks() != null) {
            event.getTasks().forEach(task -> {
                task.setSoftDelete(true);
                task.setUpdateDate(LocalDateTime.now());
            });

            taskRepository.saveAll(event.getTasks());
        }

        eventRepository.save(event);
    }


    @Override
    public Page<EventDTO> findByFilters(Pageable pageable,
                                        String eventType,
                                        String eventState,
                                        Boolean isActive,
                                        String searchValue,
                                        LocalDate creationStart,
                                        LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (eventType != null) {
            filters.put("eventType", eventType);
        }

        if (eventState != null) {
            filters.put("eventState", eventState);
        }

        if (isActive != null) {
            filters.put("softDelete", !isActive);
        }

        Specification<EventEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<EventEntity> searchSpec = specification.valueDynamicFilter(searchValue, EVENT_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<EventEntity> dateSpec = specification.filterBetween(creationStart, creationEnd, "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return eventRepository.findAll(spec, pageable)
                .map(event -> {
                    EventDTO dto = new EventDTO();
                    dto.setIdEvent(event.getIdEvent());
                    dto.setTitle(event.getTitle());
                    dto.setDescription(event.getDescription());
                    dto.setEventType(event.getEventType());
                    dto.setStartDate(event.getStartDate());
                    dto.setEndDate(event.getEndDate());
                    dto.setStatus(event.getStatus());
                    dto.setSoftDelete(event.getSoftDelete());
                    dto.setClient(modelMapper.map(event.getClient(), ClientDTO.class));
                    //dto.setLocation(modelMapper.map(event.getLocation(), LocationDTO.class));
                    dto.setCreationDate(event.getCreationDate());
                    dto.setUpdateDate(event.getUpdateDate());

                    dto.setEmployees(event.getEmployees() != null
                            ? event.getEmployees().stream().map(EmployeeEntity::getIdEmployee).toList()
                            : List.of());

                    dto.setGuests(event.getGuests() != null
                            ? event.getGuests().stream().map(GuestEntity::getIdGuest).toList()
                            : List.of());

                    dto.setSuppliers(event.getSuppliers() != null
                            ? event.getSuppliers().stream().map(SupplierEntity::getIdSupplier).toList()
                            : List.of());

                    dto.setTasks(event.getTasks() != null
                            ? event.getTasks().stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList()
                            : List.of());

                    return dto;
                });
    }


    private Set<EmployeeEntity> getEmployeesFromIds(List<Long> ids) {
        return new HashSet<>(employeeRepository.findAllById(ids));
    }

    private Set<SupplierEntity> getSuppliersFromIds(List<Long> ids) {
        return new HashSet<>(supplierRepository.findAllById(ids));
    }

    private List<TaskEntity> getTasksFromIds(List<Long> ids) {
        return taskRepository.findAllById(ids);
    }

    private Set<GuestEntity> getGuestsFromIds(List<Long> ids) {
        return new HashSet<>(guestRepository.findAllById(ids));
    }
}
