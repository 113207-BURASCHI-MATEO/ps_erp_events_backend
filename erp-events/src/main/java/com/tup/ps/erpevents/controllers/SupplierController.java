package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.supplier.SupplierDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPostDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPutDTO;
import com.tup.ps.erpevents.services.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.time.LocalDate;

@RestController
@RequestMapping("/suppliers")
@Validated
@AllArgsConstructor
@Tag(name = "Proveedores", description = "Gestión de proveedores del sistema")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Operation(summary = "Obtener todos los proveedores")
    @GetMapping("")
    public ResponseEntity<Page<SupplierDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "100") int size,
                                                    @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                    @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                    @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(supplierService.findAll(pageable));
    }

    @Operation(summary = "Obtener proveedor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getById(@PathVariable Long id) {
        return supplierService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }

    @Operation(summary = "Crear nuevo proveedor")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SupplierDTO> create(@Valid @RequestBody SupplierPostDTO supplierPostDTO) {
        SupplierDTO created = supplierService.save(supplierPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar proveedor")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SupplierDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody SupplierPutDTO supplierPutDTO) {
        return ResponseEntity.ok(supplierService.update(id, supplierPutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) un proveedor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar proveedores")
    @GetMapping("/filter")
    public ResponseEntity<Page<SupplierDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String supplierType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate creationDateStart,
            @RequestParam(required = false) LocalDate creationDateEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(supplierService.findByFilters(pageable, supplierType, isActive, searchValue, creationDateStart, creationDateEnd));
    }
}

