package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import com.tup.ps.erpevents.entities.ClientEntity;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.repositories.ClientRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.ClientServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GenericSpecification<ClientEntity> specification;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientEntity clientEntity;
    private ClientDTO clientDTO;
    private ClientPostDTO clientPostDTO;
    private ClientPutDTO clientPutDTO;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientEntity();
        clientEntity.setIdClient(1L);
        clientEntity.setFirstName("Jane");
        clientEntity.setLastName("Doe");
        clientEntity.setEmail("jane@mail.com");
        clientEntity.setPhoneNumber("123456789");
        clientEntity.setAliasCbu("ALIAS123");
        clientEntity.setSoftDelete(false);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setIdEvent(1L);
        clientEntity.setEvents(List.of(eventEntity));

        clientDTO = new ClientDTO();
        clientDTO.setIdClient(1L);
        clientDTO.setFirstName("Jane");
        clientDTO.setLastName("Doe");
        clientDTO.setEmail("jane@mail.com");
        clientDTO.setPhoneNumber("123456789");
        clientDTO.setAliasCbu("ALIAS123");
        clientDTO.setEvents(List.of(1L));

        clientPostDTO = new ClientPostDTO();
        clientPostDTO.setFirstName("Jane");
        clientPostDTO.setLastName("Doe");
        clientPostDTO.setEmail("jane@mail.com");
        clientPostDTO.setPhoneNumber("123456789");
        clientPostDTO.setAliasCbu("ALIAS123");

        clientPutDTO = new ClientPutDTO();
        clientPutDTO.setFirstName("JaneUpdated");
        clientPutDTO.setLastName("DoeUpdated");
    }

    @Test
    @DisplayName("CL-001/Should return all clients")
    void testFindAll() {
        Page<ClientEntity> page = new PageImpl<>(List.of(clientEntity));
        given(clientRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(ClientEntity.class), eq(ClientDTO.class))).willReturn(clientDTO);

        Page<ClientDTO> result = clientService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Jane", result.getContent().get(0).getFirstName());
    }

    @Test
    @DisplayName("CL-002/Should find client by ID")
    void testFindById() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(modelMapper.map(clientEntity, ClientDTO.class)).willReturn(clientDTO);

        Optional<ClientDTO> result = clientService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    @DisplayName("CL-003/Should save client")
    void testSaveClient() {
        given(modelMapper.map(clientPostDTO, ClientEntity.class)).willReturn(clientEntity);
        given(clientRepository.save(clientEntity)).willReturn(clientEntity);
        given(modelMapper.map(clientEntity, ClientDTO.class)).willReturn(clientDTO);

        ClientDTO result = clientService.save(clientPostDTO);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
    }

    @Test
    @DisplayName("CS-004/Should update existing client")
    void testUpdateClient() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        // simulate in-place mapping for the PUT DTO
        doAnswer(invocation -> {
            ClientPutDTO source = invocation.getArgument(0);
            ClientEntity target = invocation.getArgument(1);
            target.setFirstName(source.getFirstName());
            target.setLastName(source.getLastName());
            return null;
        }).when(modelMapper).map(any(ClientPutDTO.class), any(ClientEntity.class));
        given(clientRepository.save(any(ClientEntity.class))).willReturn(clientEntity);
        given(modelMapper.map(any(ClientEntity.class), eq(ClientDTO.class))).willReturn(clientDTO);

        ClientDTO result = clientService.update(1L, clientPutDTO);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
    }

    @Test
    @DisplayName("CL-005/Should delete client")
    void testDeleteClient() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));

        assertDoesNotThrow(() -> clientService.delete(1L));
        verify(clientRepository).save(clientEntity);
        assertTrue(clientEntity.getSoftDelete());
    }

    @Test
    @DisplayName("CL-006/Should throw EntityNotFoundException on update")
    void testUpdateClientNotFound() {
        given(clientRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.update(1L, clientPutDTO));
    }

    @Test
    @DisplayName("CL-007/Should throw EntityNotFoundException on delete")
    void testDeleteClientNotFound() {
        given(clientRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.delete(1L));
    }

    @Test
    @DisplayName("CL-008/Should find by filters with documentType and isActive")
    void testFindByFiltersWithDocumentTypeAndIsActive() {
        Page<ClientEntity> page = new PageImpl<>(List.of(clientEntity));
        given(specification.dynamicFilter(anyMap())).willReturn((root, query, cb) -> null);
        given(clientRepository.findAll((Specification<ClientEntity>) any(), any(Pageable.class))).willReturn(page);
        given(modelMapper.map(any(ClientEntity.class), eq(ClientDTO.class))).willReturn(clientDTO);

        Page<ClientDTO> result = clientService.findByFilters(Pageable.unpaged(), "DNI", true, null, null, null);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("CL-009/Should throw exception when only one date provided")
    void testFindByFiltersWithOneDate() {
        assertThrows(ResponseStatusException.class, () ->
                clientService.findByFilters(Pageable.unpaged(), null, null, null, LocalDate.now(), null));
    }

    @Test
    @DisplayName("CL-010/Should throw exception when creationStart is after creationEnd")
    void testFindByFiltersInvalidDateRange() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1);

        assertThrows(ResponseStatusException.class, () ->
                clientService.findByFilters(Pageable.unpaged(), null, null, null, start, end));
    }

    @Test
    @DisplayName("CL-011/Should find by document type and number")
    void testFindByDocumentTypeAndNumber() {
        given(clientRepository.findBySoftDeleteFalseAndDocumentTypeAndDocumentNumber(DocumentType.DNI, "12345678"))
                .willReturn(Optional.of(clientEntity));
        given(modelMapper.map(any(ClientEntity.class), eq(ClientDTO.class))).willReturn(clientDTO);

        Optional<ClientDTO> result = clientService.findByDocumentTypeAndDocumentNumber(DocumentType.DNI, "12345678");

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    @DisplayName("CL-012/Should return empty optional when client not found by doc type and number")
    void testFindByDocumentTypeAndNumberNotFound() {
        given(clientRepository.findBySoftDeleteFalseAndDocumentTypeAndDocumentNumber(DocumentType.DNI, "12345678"))
                .willReturn(Optional.empty());

        Optional<ClientDTO> result = clientService.findByDocumentTypeAndDocumentNumber(DocumentType.DNI, "12345678");

        assertTrue(result.isEmpty());
    }
}

