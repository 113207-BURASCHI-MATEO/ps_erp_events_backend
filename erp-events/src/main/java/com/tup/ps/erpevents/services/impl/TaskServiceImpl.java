package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.dtos.task.TaskPostDTO;
import com.tup.ps.erpevents.dtos.task.TaskPutDTO;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.TaskEntity;
import com.tup.ps.erpevents.repositories.EventRepository;
import com.tup.ps.erpevents.repositories.TaskRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final String[] TASK_FIELDS = {
            "title", "description"
    };

    @Autowired
    private TaskRepository taskRepository;

    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GenericSpecification<TaskEntity> specification;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Page<TaskDTO> findAll(Pageable pageable) {
        return taskRepository.findAllBySoftDelete(false, pageable)
                .map(task -> {
                    TaskDTO dto = modelMapper.map(task, TaskDTO.class);
                    dto.setIdEvent(task.getEvent().getIdEvent());
                    return dto;
                });
    }

    @Override
    public Optional<TaskDTO> findById(Long id) {
        return taskRepository.findById(id)
                .filter(task -> Boolean.FALSE.equals(task.getSoftDelete()))
                .map(task -> {
                    TaskDTO dto = modelMapper.map(task, TaskDTO.class);
                    dto.setIdEvent(task.getEvent().getIdEvent());
                    return dto;
                });
    }

    @Override
    public TaskDTO save(TaskPostDTO dto) {
        TaskEntity task = modelMapper.map(dto, TaskEntity.class);
        task.setSoftDelete(false);

        EventEntity event = eventRepository.findById(dto.getIdEvent())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));

        task.setEvent(event);

        TaskEntity saved = taskRepository.save(task);
        TaskDTO dtoResult = modelMapper.map(saved, TaskDTO.class);
        dtoResult.setIdEvent(saved.getEvent().getIdEvent());
        return dtoResult;
    }

    @Override
    public TaskDTO update(Long id, TaskPutDTO dto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        modelMapper.map(dto, task);
        TaskEntity saved = taskRepository.save(task);
        TaskDTO dtoResult = modelMapper.map(saved, TaskDTO.class);
        dtoResult.setIdEvent(saved.getEvent().getIdEvent());
        return dtoResult;
    }

    @Override
    public void delete(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        task.setSoftDelete(true);
        taskRepository.save(task);
    }

    @Override
    public Page<TaskDTO> findByFilters(Pageable pageable,
                                       String taskStatus,
                                       Boolean isActive,
                                       String searchValue,
                                       LocalDate creationStart,
                                       LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (isActive != null) {
            filters.put("softDelete", !isActive);
        }

        if (taskStatus != null) {
            filters.put("status", taskStatus);
        }

        Specification<TaskEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<TaskEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, TASK_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<TaskEntity> dateSpec = specification.filterBetween(
                    creationStart, creationEnd, "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return taskRepository.findAll(spec, pageable)
                .map(task -> {
                    TaskDTO dto = modelMapper.map(task, TaskDTO.class);
                    dto.setIdEvent(task.getEvent().getIdEvent());
                    return dto;
                });
    }
}

