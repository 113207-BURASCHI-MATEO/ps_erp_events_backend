package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import com.tup.ps.erpevents.services.GuestService;
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
@RequestMapping("/guests")
@Validated
@AllArgsConstructor
@Tag(name = "Invitados", description = "Gestión de invitados del sistema")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Operation(summary = "Obtener todos los invitados")
    @GetMapping("")
    public ResponseEntity<Page<GuestDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                 @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                 @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(guestService.findAll(pageable));
    }

    @Operation(summary = "Obtener invitado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getById(@PathVariable Long id) {
        return guestService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitado no encontrado"));
    }

    @Operation(summary = "Crear nuevo invitado")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestDTO> create(@Valid @RequestBody GuestPostDTO guestPostDTO) {
        GuestDTO created = guestService.save(guestPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar invitado")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody GuestPutDTO guestPutDTO) {
        return ResponseEntity.ok(guestService.update(id, guestPutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) un invitado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar invitados")
    @GetMapping("/filter")
    public ResponseEntity<Page<GuestDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String guestType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate creationStart,
            @RequestParam(required = false) LocalDate creationEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(guestService.findByFilters(pageable, guestType, isActive, searchValue, creationStart, creationEnd));
    }
}

