package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.employee.EmployeeDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePostDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePutDTO;
import com.tup.ps.erpevents.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/employees")
@Validated
@AllArgsConstructor
@Tag(name = "Empleados", description = "Gestión de empleados del sistema")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Obtener todos los empleados")
    @GetMapping("")
    public ResponseEntity<Page<EmployeeDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                    @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                    @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @Operation(summary = "Obtener empleado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
    }

    @Operation(summary = "Crear nuevo empleado")
    @PostMapping(consumes = "application/json" , produces = "application/json")
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeePostDTO employeePostDTO) {
        EmployeeDTO created = employeeService.save(employeePostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar empleado")
    @PutMapping(value = "/{id}", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody EmployeePutDTO employeePutDTO) {
        return ResponseEntity.ok(employeeService.update(id, employeePutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) un empleado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar empleados")
    @GetMapping("/filter")
    public ResponseEntity<Page<EmployeeDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String documentType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate birthdateStart,
            @RequestParam(required = false) LocalDate birthdateEnd,
            @RequestParam(name = "sort", defaultValue = "createdDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(employeeService.findByFilters(pageable, documentType, isActive, searchValue, birthdateStart, birthdateEnd));
    }
}

