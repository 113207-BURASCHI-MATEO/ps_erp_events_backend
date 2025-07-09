package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.enums.EventStatus;
import com.tup.ps.erpevents.services.EventService;
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
import java.util.List;

@RestController
@RequestMapping("/events")
@Validated
@AllArgsConstructor
@Tag(name = "Eventos", description = "Gestión de eventos del sistema")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Obtener todos los eventos")
    @GetMapping("")
    public ResponseEntity<Page<EventDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "100") int size,
                                                 @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                 @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                 @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(eventService.findAll(pageable));
    }

    @Operation(summary = "Obtener evento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
    }

    @Operation(summary = "Crear nuevo evento")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<EventDTO> create(@Valid @RequestBody EventPostDTO eventPostDTO) {
        EventDTO created = eventService.save(eventPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar evento")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EventDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody EventPutDTO eventPutDTO) {
        return ResponseEntity.ok(eventService.update(id, eventPutDTO));
    }

    @Operation(summary = "Cambiar el estado al evento")
    @PutMapping("/{idEvent}/status")
    public ResponseEntity<EventDTO> eventStatus(
            @PathVariable Long idEvent,
            @RequestBody EventStatus eventStatus) {
        return ResponseEntity.ok(eventService.eventStatus(idEvent, eventStatus));
    }

    @Operation(summary = "Eliminar (baja lógica) un evento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar eventos")
    @GetMapping("/filter")
    public ResponseEntity<Page<EventDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String eventStatus,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate startDateStart,
            @RequestParam(required = false) LocalDate startDateEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(eventService.findByFilters(pageable, eventType, eventStatus, isActive, searchValue, startDateStart, startDateEnd));
    }
}

