package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.supplier.SupplierDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPostDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPutDTO;
import com.tup.ps.erpevents.entities.SupplierEntity;
import com.tup.ps.erpevents.repositories.SupplierRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private static final String[] SUPPLIER_FIELDS = {
            "name", "cuit", "email", "phoneNumber", "aliasOrCbu"
    };

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GenericSpecification<SupplierEntity> specification;

    @Override
    public Page<SupplierDTO> findAll(Pageable pageable) {
        return supplierRepository.findAllBySoftDelete(false, pageable)
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class));
    }

    @Override
    public Optional<SupplierDTO> findById(Long id) {
        return supplierRepository.findById(id)
                .filter(supplier -> Boolean.FALSE.equals(supplier.getSoftDelete()))
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class));
    }

    @Override
    public SupplierDTO save(SupplierPostDTO dto) {
        SupplierEntity supplier = modelMapper.map(dto, SupplierEntity.class);
        supplier.setSoftDelete(false);
        return modelMapper.map(supplierRepository.save(supplier), SupplierDTO.class);
    }

    @Override
    public SupplierDTO update(Long id, SupplierPutDTO dto) {
        SupplierEntity supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        modelMapper.map(dto, supplier);
        return modelMapper.map(supplierRepository.save(supplier), SupplierDTO.class);
    }

    @Override
    public void delete(Long id) {
        SupplierEntity supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        supplier.setSoftDelete(true);
        supplierRepository.save(supplier);
    }

    @Override
    public Page<SupplierDTO> findByFilters(Pageable pageable,
                                           String supplierType,
                                           Boolean isActive,
                                           String searchValue,
                                           LocalDate creationDateStart,
                                           LocalDate creationDateEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (supplierType != null) {
            filters.put("supplierType", supplierType);
        }

        if (isActive != null) {
            filters.put("softDelete", !isActive); // true → dado de baja
        }

        Specification<SupplierEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<SupplierEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, SUPPLIER_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationDateStart == null) ^ (creationDateEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationDateStart != null && creationDateEnd != null) {
            if (creationDateStart.isAfter(creationDateEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationDateStart no puede ser mayor a creationDateEnd");
            }
            Specification<SupplierEntity> dateSpec = specification.filterBetween(
                    creationDateStart.atStartOfDay(), creationDateEnd.atTime(23, 59, 59), "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return supplierRepository.findAll(spec, pageable)
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class));
    }

}
