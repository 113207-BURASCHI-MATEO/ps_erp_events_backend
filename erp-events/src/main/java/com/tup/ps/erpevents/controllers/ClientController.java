package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.services.ClientService;
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
@RequestMapping("/clients")
@Validated
@AllArgsConstructor
@Tag(name = "Clientes", description = "Gestión de clientes del sistema")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Obtener todos los clientes")
    @GetMapping("")
    public ResponseEntity<Page<ClientDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "100") int size,
                                                  @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                  @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                  @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    @Operation(summary = "Obtener cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    @Operation(summary = "Crear nuevo cliente")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientPostDTO clientPostDTO) {
        ClientDTO created = clientService.save(clientPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar cliente")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody ClientPutDTO clientPutDTO) {
        return ResponseEntity.ok(clientService.update(id, clientPutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) un cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar clientes")
    @GetMapping("/filter")
    public ResponseEntity<Page<ClientDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String documentType,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate creationStart,
            @RequestParam(required = false) LocalDate creationEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(clientService.findByFilters(pageable, documentType, isActive, searchValue, creationStart, creationEnd));
    }

    @Operation(summary = "Buscar cliente activo por tipo y número de documento")
    @GetMapping("/exists")
    public ResponseEntity<ClientDTO> findByDocument(
            @RequestParam DocumentType documentType,
            @RequestParam String documentNumber) {

        return clientService.findByDocumentTypeAndDocumentNumber(documentType, documentNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

}

