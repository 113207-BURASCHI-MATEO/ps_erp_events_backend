package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPostDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPutDTO;
import com.tup.ps.erpevents.enums.AccountingConcept;
import com.tup.ps.erpevents.services.ConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.time.LocalDate;

@RestController
@RequestMapping("/concepts")
@Validated
@AllArgsConstructor
@Tag(name = "Movimientos", description = "Gestión del presupuesto de los eventos del sistema")
public class ConceptController {

    @Autowired
    private ConceptService conceptService;

    @Operation(summary = "Obtener todos los conceptos")
    @GetMapping("")
    public ResponseEntity<Page<ConceptDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "100") int size,
                                                   @RequestParam(name = "sort", defaultValue = "accountingDate") String sortProperty,
                                                   @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(conceptService.findAll(pageable));
    }

    @Operation(summary = "Obtener concepto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConceptDTO> getById(@PathVariable Long id) {
        return conceptService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Concepto no encontrado"));
    }

    @Operation(summary = "Crear nuevo concepto")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ConceptDTO> create(@Valid @RequestBody ConceptPostDTO conceptPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conceptService.save(conceptPostDTO));
    }

    @Operation(summary = "Actualizar concepto")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ConceptDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody ConceptPutDTO conceptPutDTO) {
        return ResponseEntity.ok(conceptService.update(id, conceptPutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) un concepto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conceptService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar conceptos")
    @GetMapping("/filter")
    public ResponseEntity<Page<ConceptDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) AccountingConcept concept,
            @RequestParam(required = false) Long idAccount,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate dateStart,
            @RequestParam(required = false) LocalDate dateEnd,
            @RequestParam(name = "sort", defaultValue = "accountingDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(conceptService.findByFilters(pageable, concept, idAccount, searchValue, dateStart, dateEnd));
    }

    @Operation(summary = "Listar conceptos por ID de cuenta")
    @GetMapping("/account/{idAccount}")
    public ResponseEntity<Page<ConceptDTO>> findByAccountId(@PathVariable Long idAccount,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "100") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(conceptService.findByAccountId(pageable, idAccount));
    }

}
