package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.location.LocationPostDTO;
import com.tup.ps.erpevents.dtos.location.LocationPutDTO;
import com.tup.ps.erpevents.entities.LocationEntity;
import com.tup.ps.erpevents.repositories.LocationRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.LocationService;
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
public class LocationServiceImpl implements LocationService {

    private static final String[] LOCATION_FIELDS = {
            "address", "latitude", "longitude"
    };

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    private final GenericSpecification<LocationEntity> specification;

    @Override
    public Page<LocationDTO> findAll(Pageable pageable) {
        return locationRepository.findAllBySoftDelete(false, pageable)
                .map(location -> modelMapper.map(location, LocationDTO.class));
    }

    @Override
    public Optional<LocationDTO> findById(Long id) {
        return locationRepository.findById(id)
                .filter(location -> Boolean.FALSE.equals(location.getSoftDelete()))
                .map(location -> modelMapper.map(location, LocationDTO.class));
    }

    @Override
    public LocationDTO save(LocationPostDTO dto) {
        LocationEntity location = modelMapper.map(dto, LocationEntity.class);
        location.setSoftDelete(false);
        return modelMapper.map(locationRepository.save(location), LocationDTO.class);
    }

    @Override
    public LocationDTO update(Long id, LocationPutDTO dto) {
        LocationEntity location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada"));
        modelMapper.map(dto, location);
        return modelMapper.map(locationRepository.save(location), LocationDTO.class);
    }

    @Override
    public void delete(Long id) {
        LocationEntity location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada"));
        location.setSoftDelete(true);
        locationRepository.save(location);
    }

    @Override
    public Page<LocationDTO> findByFilters(Pageable pageable,
                                           Boolean isActive,
                                           String searchValue,
                                           LocalDate creationStart,
                                           LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (isActive != null) {
            filters.put("softDelete", !isActive);
        }

        Specification<LocationEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<LocationEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, LOCATION_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<LocationEntity> dateSpec = specification.filterBetween(creationStart, creationEnd, "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return locationRepository.findAll(spec, pageable)
                .map(location -> modelMapper.map(location, LocationDTO.class));
    }
}

