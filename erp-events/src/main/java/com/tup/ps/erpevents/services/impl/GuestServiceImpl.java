package com.tup.ps.erpevents.services.impl;


import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import com.tup.ps.erpevents.entities.GuestEntity;
import com.tup.ps.erpevents.repositories.GuestRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.GuestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private static final String[] GUEST_FIELDS = {
            "firstName", "lastName", "email", "note"
    };

    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;
    private final GenericSpecification<GuestEntity> specification;

    @Override
    public Page<GuestDTO> findAll(Pageable pageable) {
        return guestRepository.findAllBySoftDelete(false, pageable)
                .map(guest -> modelMapper.map(guest, GuestDTO.class));
    }

    @Override
    public Optional<GuestDTO> findById(Long id) {
        return guestRepository.findById(id)
                .filter(guest -> Boolean.FALSE.equals(guest.getSoftDelete()))
                .map(guest -> modelMapper.map(guest, GuestDTO.class));
    }

    @Override
    public GuestDTO save(GuestPostDTO dto) {
        GuestEntity guest = modelMapper.map(dto, GuestEntity.class);
        guest.setSoftDelete(false);
        return modelMapper.map(guestRepository.save(guest), GuestDTO.class);
    }

    @Override
    public GuestDTO update(Long id, GuestPutDTO dto) {
        GuestEntity guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invitado no encontrado"));
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
}
