package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.event.account.AccountDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.entities.AccountEntity;
import com.tup.ps.erpevents.entities.ConceptEntity;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.repositories.AccountRespository;
import com.tup.ps.erpevents.repositories.EventRepository;
import com.tup.ps.erpevents.services.impl.AccountServiceImpl;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    private AccountRespository accountRespository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountEntity accountEntity;
    private AccountDTO accountDTO;
    private ConceptEntity conceptEntity;
    private ConceptDTO conceptDTO;
    private EventEntity eventEntity;

    @BeforeEach
    void setUp() {
        accountEntity = new AccountEntity();
        accountEntity.setIdAccount(1L);
        accountEntity.setBalance(BigDecimal.TEN);
        accountEntity.setSoftDelete(false);
        accountEntity.setCreationDate(LocalDateTime.now());
        accountEntity.setUpdateDate(LocalDateTime.now());

        conceptEntity = new ConceptEntity();
        conceptEntity.setIdConcept(1L);
        accountEntity.setConcepts(List.of(conceptEntity));

        accountDTO = new AccountDTO();
        accountDTO.setIdAccount(1L);

        conceptDTO = new ConceptDTO();
        conceptDTO.setIdConcept(1L);

        eventEntity = new EventEntity();
        eventEntity.setIdEvent(1L);
    }

    @Test
    @DisplayName("AS-001/Should return all accounts")
    void testFindAll() {
        Page<AccountEntity> page = new PageImpl<>(List.of(accountEntity));
        Pageable pageable = PageRequest.of(0, 10);

        given(accountRespository.findAll(pageable)).willReturn(page);
        given(modelMapper.map(accountEntity, AccountDTO.class)).willReturn(accountDTO);
        given(modelMapper.map(conceptEntity, ConceptDTO.class)).willReturn(conceptDTO);

        Page<AccountDTO> result = accountService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getIdAccount());
    }

    @Test
    @DisplayName("AS-002/Should return account by ID")
    void testFindById() {
        given(accountRespository.findById(1L)).willReturn(Optional.of(accountEntity));
        given(modelMapper.map(accountEntity, AccountDTO.class)).willReturn(accountDTO);
        given(modelMapper.map(conceptEntity, ConceptDTO.class)).willReturn(conceptDTO);

        Optional<AccountDTO> result = accountService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdAccount());
    }

    @Test
    @DisplayName("AS-003/Should soft delete account")
    void testDeleteAccount() {
        given(accountRespository.findById(1L)).willReturn(Optional.of(accountEntity));

        accountService.delete(1L);

        assertTrue(accountEntity.getSoftDelete());
        verify(accountRespository).save(accountEntity);
    }

    @Test
    @DisplayName("AS-004/Should throw exception when account not found")
    void testDeleteAccountNotFound() {
        given(accountRespository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.delete(1L));
    }

    @Test
    @DisplayName("AS-005/Should return account by event ID")
    void testFindByEventId() {
        Pageable pageable = PageRequest.of(0, 10);

        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(accountRespository.findByEvent(eventEntity)).willReturn(Optional.of(accountEntity));
        given(modelMapper.map(accountEntity, AccountDTO.class)).willReturn(accountDTO);
        given(modelMapper.map(conceptEntity, ConceptDTO.class)).willReturn(conceptDTO);

        AccountDTO result = accountService.findByEventId(pageable, 1L);

        assertEquals(1L, result.getIdAccount());
    }

    @Test
    @DisplayName("AS-006/Should throw exception if event not found")
    void testFindByEventIdEventNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        given(eventRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.findByEventId(pageable, 1L));
    }

    @Test
    @DisplayName("AS-007/Should throw exception if account by event not found")
    void testFindByEventIdAccountNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(accountRespository.findByEvent(eventEntity)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.findByEventId(pageable, 1L));
    }
}
