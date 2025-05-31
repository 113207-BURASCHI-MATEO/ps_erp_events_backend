package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.supplier.SupplierDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPostDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPutDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.*;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.SupplierServiceImpl;
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
public class SupplierServiceImplTest {

    @Mock private SupplierRepository supplierRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private GenericSpecification<SupplierEntity> specification;

    @InjectMocks private SupplierServiceImpl supplierService;

    private SupplierEntity supplierEntity;
    private SupplierDTO supplierDTO;
    private SupplierPostDTO supplierPostDTO;
    private SupplierPutDTO supplierPutDTO;

    @BeforeEach
    void setUp() {
        supplierEntity = new SupplierEntity();
        supplierEntity.setIdSupplier(1L);
        supplierEntity.setSoftDelete(false);

        supplierDTO = new SupplierDTO();
        supplierDTO.setIdSupplier(1L);

        supplierPostDTO = new SupplierPostDTO();
        supplierPutDTO = new SupplierPutDTO();
    }

    @Test
    @DisplayName("SU-001/Should return all suppliers")
    void testFindAll() {
        Page<SupplierEntity> page = new PageImpl<>(List.of(supplierEntity));
        given(supplierRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(), eq(SupplierDTO.class))).willReturn(supplierDTO);

        Page<SupplierDTO> result = supplierService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("SU-002/Should return supplier by ID")
    void testFindById() {
        given(supplierRepository.findById(1L)).willReturn(Optional.of(supplierEntity));
        given(modelMapper.map(any(), eq(SupplierDTO.class))).willReturn(supplierDTO);

        Optional<SupplierDTO> result = supplierService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdSupplier());
    }

    @Test
    @DisplayName("SU-003/Should save new supplier")
    void testSaveSupplier() {
        given(modelMapper.map(supplierPostDTO, SupplierEntity.class)).willReturn(supplierEntity);
        given(supplierRepository.save(any())).willReturn(supplierEntity);
        given(modelMapper.map(supplierEntity, SupplierDTO.class)).willReturn(supplierDTO);

        SupplierDTO result = supplierService.save(supplierPostDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("SU-004/Should update existing supplier")
    void testUpdateSupplier() {
        given(supplierRepository.findById(1L)).willReturn(Optional.of(supplierEntity));
        willDoNothing().given(modelMapper).map(supplierPutDTO, supplierEntity);
        given(supplierRepository.save(supplierEntity)).willReturn(supplierEntity);
        given(modelMapper.map(supplierEntity, SupplierDTO.class)).willReturn(supplierDTO);

        SupplierDTO result = supplierService.update(1L, supplierPutDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("SU-005/Should delete supplier")
    void testDeleteSupplier() {
        given(supplierRepository.findById(1L)).willReturn(Optional.of(supplierEntity));

        assertDoesNotThrow(() -> supplierService.delete(1L));
        assertTrue(supplierEntity.getSoftDelete());
    }

    @Test
    @DisplayName("SU-006/Should filter suppliers with date validation")
    void testFindByFiltersWithDateRange() {
        LocalDate start = LocalDate.now().minusDays(5);
        LocalDate end = LocalDate.now();

        Page<SupplierEntity> page = new PageImpl<>(List.of(supplierEntity));
        given(specification.dynamicFilter(any())).willReturn(Specification.where(null));
        given(specification.valueDynamicFilter(eq(""), eq("name"), eq("cuit"), eq("email"), eq("phoneNumber"), eq("aliasOrCbu")))
                .willReturn(mock(Specification.class));
        given(specification.filterBetween(any(), any(), eq("creationDate"))).willReturn(Specification.where(null));
        given(supplierRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);
        given(modelMapper.map(any(), eq(SupplierDTO.class))).willReturn(supplierDTO);

        Page<SupplierDTO> result = supplierService.findByFilters(Pageable.unpaged(), null, true, "", start, end);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("SU-007/Should throw when only one date provided")
    void testFindByFiltersWithOneDateOnly() {
        assertThrows(ResponseStatusException.class, () ->
                supplierService.findByFilters(Pageable.unpaged(), null, true, "", LocalDate.now(), null));
    }

    @Test
    @DisplayName("SU-008/Should throw when start date is after end date")
    void testFindByFiltersWithInvalidDateRange() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1);

        assertThrows(ResponseStatusException.class, () ->
                supplierService.findByFilters(Pageable.unpaged(), null, true, "", start, end));
    }
}

