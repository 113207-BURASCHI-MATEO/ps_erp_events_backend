package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.dtos.task.TaskPostDTO;
import com.tup.ps.erpevents.dtos.task.TaskPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public interface TaskService {

    /**
     * Obtiene todas las tareas paginadas.
     *
     * @param pageable información de paginación
     * @return página con tareas
     */
    Page<TaskDTO> findAll(Pageable pageable);

    /**
     * Busca una tarea por ID.
     *
     * @param id ID de la tarea
     * @return tarea encontrada o vacío si no existe
     */
    Optional<TaskDTO> findById(Long id);

    /**
     * Crea una nueva tarea.
     *
     * @param taskDTO datos de la tarea
     * @return tarea creada
     */
    TaskDTO save(TaskPostDTO taskDTO);

    /**
     * Actualiza una tarea existente.
     *
     * @param id      ID de la tarea
     * @param taskDTO nuevos datos de la tarea
     * @return tarea actualizada
     */
    TaskDTO update(Long id, TaskPutDTO taskDTO);

    /**
     * Realiza una baja lógica de la tarea (soft delete).
     *
     * @param id ID de la tarea
     */
    void delete(Long id);

    /**
     * Busca tareas con filtros dinámicos.
     *
     * @param pageable          información de paginación
     * @param taskStatus        estado de la tarea
     * @param isActive          estado lógico (activo/inactivo)
     * @param searchValue       término de búsqueda general
     * @param creationStart     fecha inicial de creación
     * @param creationEnd       fecha final de creación
     * @return página con resultados filtrados
     */
    Page<TaskDTO> findByFilters(Pageable pageable,
                                String taskStatus,
                                Boolean isActive,
                                String searchValue,
                                LocalDate creationStart,
                                LocalDate creationEnd);
}

