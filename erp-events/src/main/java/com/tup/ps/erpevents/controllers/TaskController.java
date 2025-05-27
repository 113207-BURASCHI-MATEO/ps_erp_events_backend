package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.dtos.task.TaskPostDTO;
import com.tup.ps.erpevents.dtos.task.TaskPutDTO;
import com.tup.ps.erpevents.enums.TaskStatus;
import com.tup.ps.erpevents.services.TaskService;
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
@RequestMapping("/tasks")
@Validated
@AllArgsConstructor
@Tag(name = "Tareas", description = "Gestión de tareas del sistema")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Obtener todas las tareas")
    @GetMapping("")
    public ResponseEntity<Page<TaskDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(taskService.findAll(pageable));
    }

    @Operation(summary = "Obtener tarea por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));
    }

    @Operation(summary = "Crear nueva tarea")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskPostDTO taskPostDTO) {
        TaskDTO created = taskService.save(taskPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar tarea")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id,
                                          @Valid @RequestBody TaskPutDTO taskPutDTO) {
        return ResponseEntity.ok(taskService.update(id, taskPutDTO));
    }

    @Operation(summary = "Eliminar (baja lógica) una tarea")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar tareas")
    @GetMapping("/filter")
    public ResponseEntity<Page<TaskDTO>> findByFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate creationStart,
            @RequestParam(required = false) LocalDate creationEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(taskService.findByFilters(pageable, status, isActive, searchValue, creationStart, creationEnd));
    }

    @Operation(summary = "Actualizar el estado de tarea")
    @PatchMapping(value = "/{idTask}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long idTask, @RequestBody TaskStatus taskStatus) {
        return ResponseEntity.ok(taskService.updateTaskStatus(idTask, taskStatus));
    }

    @Operation(summary = "Cargar tareas a un evento")
    @PostMapping(value = "/event/{idEvent}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<TaskDTO>> saveTasks(@PathVariable Long idEvent,
                                                     @Valid @RequestBody List<TaskPostDTO> taskPostDTOS) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTasksToEvent(taskPostDTOS, idEvent));
    }

    @Operation(summary = "Obtener tareas de un evento")
    @GetMapping( "/event/{idEvent}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable Long idEvent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.getTasksFromEvent(idEvent));
    }
}

