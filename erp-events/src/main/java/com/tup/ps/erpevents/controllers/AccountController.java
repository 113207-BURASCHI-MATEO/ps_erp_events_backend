package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.event.account.AccountDTO;
import com.tup.ps.erpevents.services.AccountService;
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
@RequestMapping("/accounts")
@Validated
@AllArgsConstructor
@Tag(name = "Cuentas", description = "Gestión del presupuesto de los eventos del sistema")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Obtener todas las cuentas")
    @GetMapping("")
    public ResponseEntity<Page<AccountDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "100") int size,
                                                   @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                   @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(accountService.findAll(pageable));
    }

    @Operation(summary = "Obtener cuenta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
    }

    @Operation(summary = "Crear cuenta")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(accountDTO));
    }

    @Operation(summary = "Actualizar cuenta")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.update(id, accountDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) una cuenta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar cuentas")
    @GetMapping("/filter")
    public ResponseEntity<Page<AccountDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate creationStart,
            @RequestParam(required = false) LocalDate creationEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(accountService.findByFilters(pageable, isActive, searchValue, creationStart, creationEnd));
    }

    @Operation(summary = "Listar cuenta por ID de evento")
    @GetMapping("/event/{idEvent}")
    public ResponseEntity<AccountDTO> findByEventId(@PathVariable Long idEvent,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "100") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(accountService.findByEventId(pageable, idEvent));
    }


}
