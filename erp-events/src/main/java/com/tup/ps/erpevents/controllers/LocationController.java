package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.location.LocationPostDTO;
import com.tup.ps.erpevents.dtos.location.LocationPutDTO;
import com.tup.ps.erpevents.services.LocationService;
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
@RequestMapping("/locations")
@Validated
@AllArgsConstructor
@Tag(name = "Ubicaciones", description = "Gestión de ubicaciones del sistema")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Obtener todas las ubicaciones")
    @GetMapping("")
    public ResponseEntity<Page<LocationDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                    @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                    @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(locationService.findAll(pageable));
    }

    @Operation(summary = "Obtener ubicación por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getById(@PathVariable Long id) {
        return locationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
    }

    @Operation(summary = "Crear nueva ubicación")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<LocationDTO> create(@Valid @RequestBody LocationPostDTO locationPostDTO) {
        LocationDTO created = locationService.save(locationPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar ubicación")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LocationDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody LocationPutDTO locationPutDTO) {
        return ResponseEntity.ok(locationService.update(id, locationPutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) una ubicación")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar ubicaciones")
    @GetMapping("/filter")
    public ResponseEntity<Page<LocationDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate creationStart,
            @RequestParam(required = false) LocalDate creationEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(locationService.findByFilters(pageable, isActive, searchValue, creationStart, creationEnd));
    }
}

