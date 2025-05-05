package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import com.tup.ps.erpevents.entities.ClientEntity;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.ClientRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.ClientService;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final String[] CLIENT_FIELDS = {
            "firstName", "lastName", "email", "phoneNumber", "aliasOrCbu"
    };

    @Autowired
    private ClientRepository clientRepository;
    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenericSpecification<ClientEntity> specification;

    @Override
    public Page<ClientDTO> findAll(Pageable pageable) {
        return clientRepository.findAllBySoftDelete(false, pageable)
                .map(client -> {
                    ClientDTO dto = modelMapper.map(client, ClientDTO.class);
                    dto.setEvents(client.getEvents().stream()
                            .map(EventEntity::getIdEvent)
                            .toList());
                    return dto;
                });
    }

    @Override
    public Optional<ClientDTO> findById(Long id) {
        return clientRepository.findById(id)
                .filter(client -> Boolean.FALSE.equals(client.getSoftDelete()))
                .map(client -> {
                    ClientDTO dto = modelMapper.map(client, ClientDTO.class);
                    dto.setEvents(client.getEvents().stream()
                            .map(EventEntity::getIdEvent)
                            .toList());
                    return dto;
                });
    }

    @Override
    public ClientDTO save(ClientPostDTO dto) {
        ClientEntity client = modelMapper.map(dto, ClientEntity.class);
        client.setSoftDelete(false);
        ClientEntity saved = clientRepository.save(client);
        ClientDTO dtoResult = modelMapper.map(saved, ClientDTO.class);
        dtoResult.setEvents(saved.getEvents().stream()
                .map(EventEntity::getIdEvent)
                .toList());
        return dtoResult;
    }

    @Override
    public ClientDTO update(Long id, ClientPutDTO dto) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        modelMapper.map(dto, client);
        ClientEntity saved = clientRepository.save(client);
        ClientDTO dtoResult = modelMapper.map(saved, ClientDTO.class);
        dtoResult.setEvents(saved.getEvents().stream()
                .map(EventEntity::getIdEvent)
                .toList());
        return dtoResult;
    }

    @Override
    public void delete(Long id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        client.setSoftDelete(true);
        clientRepository.save(client);
    }

    @Override
    public Page<ClientDTO> findByFilters(Pageable pageable,
                                         Boolean isActive,
                                         String searchValue,
                                         LocalDate creationStart,
                                         LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (isActive != null) {
            filters.put("softDelete", !isActive);
        }

        Specification<ClientEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<ClientEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, CLIENT_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<ClientEntity> dateSpec = specification.filterBetween(creationStart, creationEnd, "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return clientRepository.findAll(spec, pageable)
                .map(client -> {
                    ClientDTO dto = modelMapper.map(client, ClientDTO.class);
                    dto.setEvents(client.getEvents().stream()
                            .map(EventEntity::getIdEvent)
                            .toList());
                    return dto;
                });
    }
}

