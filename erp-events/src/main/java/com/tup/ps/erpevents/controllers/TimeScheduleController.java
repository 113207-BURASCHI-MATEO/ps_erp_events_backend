package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.task.schedule.TimeScheduleDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePostDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePutDTO;
import com.tup.ps.erpevents.services.TimeScheduleService;
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

@RestController
@RequestMapping("/schedules")
@Validated
@AllArgsConstructor
@Tag(name = "Cronogramas", description = "Gestión de cronogramas de eventos del sistema")
public class TimeScheduleController {

    @Autowired
    private TimeScheduleService timeScheduleService;

    @Operation(summary = "Obtener todos los cronogramas")
    @GetMapping
    public ResponseEntity<Page<TimeScheduleDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(name = "sort", defaultValue = "idTimeSchedule") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "ASC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(timeScheduleService.findAll(pageable));
    }

    @Operation(summary = "Obtener un cronograma por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TimeScheduleDTO> getById(@PathVariable Long id) {
        return timeScheduleService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado"));
    }

    @Operation(summary = "Crear un nuevo cronograma")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TimeScheduleDTO> create(@Valid @RequestBody TimeSchedulePostDTO dto) {
        TimeScheduleDTO created = timeScheduleService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar un cronograma")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TimeScheduleDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TimeSchedulePutDTO dto) {
        return ResponseEntity.ok(timeScheduleService.update(id, dto));
    }

    @Operation(summary = "Eliminar un cronograma")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
