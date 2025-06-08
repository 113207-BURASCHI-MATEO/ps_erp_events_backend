package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPostDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPutDTO;
import com.tup.ps.erpevents.entities.AccountEntity;
import com.tup.ps.erpevents.entities.ConceptEntity;
import com.tup.ps.erpevents.enums.AccountingConcept;
import com.tup.ps.erpevents.repositories.AccountRespository;
import com.tup.ps.erpevents.repositories.ConceptRespository;
import com.tup.ps.erpevents.repositories.FileRepository;
import com.tup.ps.erpevents.services.impl.ConceptServiceImpl;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ConceptServiceImplTest {
    @Mock
    private ConceptRespository conceptRespository;

    @Mock
    private AccountRespository accountRespository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ConceptServiceImpl conceptService;

    private ConceptEntity conceptEntity;
    private ConceptDTO conceptDTO;
    private ConceptPostDTO conceptPostDTO;
    private ConceptPutDTO conceptPutDTO;
    private AccountEntity accountEntity;

    @BeforeEach
    void setUp() {
        accountEntity = new AccountEntity();
        accountEntity.setIdAccount(1L);
        accountEntity.setBalance(BigDecimal.ZERO);

        conceptEntity = new ConceptEntity();
        conceptEntity.setIdConcept(1L);
        conceptEntity.setAmount(BigDecimal.TEN);
        conceptEntity.setConcept(AccountingConcept.CATERING);
        conceptEntity.setAccount(accountEntity);

        conceptDTO = new ConceptDTO();
        conceptDTO.setIdConcept(1L);

        conceptPostDTO = new ConceptPostDTO();
        conceptPostDTO.setIdAccount(1L);
        conceptPostDTO.setAmount(BigDecimal.TEN);
        conceptPostDTO.setConcept(AccountingConcept.CATERING);

        conceptPutDTO = new ConceptPutDTO();
        conceptPutDTO.setIdConcept(1L);
        conceptPutDTO.setIdAccount(1L);
        conceptPutDTO.setAmount(BigDecimal.TEN);
        conceptPutDTO.setConcept(AccountingConcept.CATERING);
    }

    @Test
    @DisplayName("CS-001/Should return all concepts")
    void testFindAll() {
        Page<ConceptEntity> page = new PageImpl<>(List.of(conceptEntity));
        given(conceptRespository.findAll(Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(ConceptEntity.class), eq(ConceptDTO.class))).willReturn(conceptDTO);

        Page<ConceptDTO> result = conceptService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getIdConcept());
    }

    @Test
    @DisplayName("CS-002/Should return concept by ID")
    void testFindById() {
        given(conceptRespository.findById(1L)).willReturn(Optional.of(conceptEntity));
        given(modelMapper.map(any(ConceptEntity.class), eq(ConceptDTO.class))).willReturn(conceptDTO);

        Optional<ConceptDTO> result = conceptService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdConcept());
    }

    @Test
    @DisplayName("CS-003/Should save concept")
    void testSaveConcept() {
        given(accountRespository.findById(1L)).willReturn(Optional.of(accountEntity));
        given(modelMapper.map(conceptPostDTO, ConceptEntity.class)).willReturn(conceptEntity);
        given(conceptRespository.save(any(ConceptEntity.class))).willReturn(conceptEntity);
        given(modelMapper.map(any(ConceptEntity.class), eq(ConceptDTO.class))).willReturn(conceptDTO);

        ConceptDTO result = conceptService.save(conceptPostDTO);

        assertNotNull(result);
        verify(accountRespository).save(accountEntity);
    }

    @Test
    @DisplayName("CS-004/Should update concept")
    void testUpdateConcept() {
        given(accountRespository.findById(1L)).willReturn(Optional.of(accountEntity));
        given(conceptRespository.findById(1L)).willReturn(Optional.of(conceptEntity));
        given(modelMapper.map(conceptPutDTO, ConceptEntity.class)).willReturn(conceptEntity);
        given(conceptRespository.save(any(ConceptEntity.class))).willReturn(conceptEntity);
        given(modelMapper.map(any(ConceptEntity.class), eq(ConceptDTO.class))).willReturn(conceptDTO);

        ConceptDTO result = conceptService.update(1L, conceptPutDTO);

        assertNotNull(result);
        verify(accountRespository).save(accountEntity);
    }

    @Test
    @DisplayName("CS-005/Should delete concept")
    void testDeleteConcept() {
        given(conceptRespository.findById(1L)).willReturn(Optional.of(conceptEntity));

        conceptService.delete(1L);

        assertTrue(conceptEntity.getSoftDelete());
        verify(conceptRespository).save(conceptEntity);
    }

    @Test
    @DisplayName("CS-006/Should throw when concept not found for delete")
    void testDeleteConceptNotFound() {
        given(conceptRespository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> conceptService.delete(1L));
    }

    @Test
    @DisplayName("CS-007/Should find concepts by account")
    void testFindByAccountId() {
        Page<ConceptEntity> page = new PageImpl<>(List.of(conceptEntity));
        given(accountRespository.findById(1L)).willReturn(Optional.of(accountEntity));
        given(conceptRespository.findAllByAccount(Pageable.unpaged(), accountEntity)).willReturn(page);
        given(modelMapper.map(any(ConceptEntity.class), eq(ConceptDTO.class))).willReturn(conceptDTO);

        Page<ConceptDTO> result = conceptService.findByAccountId(Pageable.unpaged(), 1L);

        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getIdConcept());
    }
}
