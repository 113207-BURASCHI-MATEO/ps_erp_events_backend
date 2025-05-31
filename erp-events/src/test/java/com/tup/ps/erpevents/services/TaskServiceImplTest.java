package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.dtos.task.TaskPostDTO;
import com.tup.ps.erpevents.dtos.task.TaskPutDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.*;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock private TaskRepository taskRepository;
    @Mock private EventRepository eventRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private GenericSpecification<TaskEntity> specification;
    @InjectMocks private TaskServiceImpl taskService;

    private TaskEntity taskEntity;
    private TaskDTO taskDTO;
    private TaskPostDTO taskPostDTO;
    private TaskPutDTO taskPutDTO;
    private EventEntity eventEntity;

    @BeforeEach
    void setUp() {
        eventEntity = new EventEntity();
        eventEntity.setIdEvent(1L);

        taskEntity = new TaskEntity();
        taskEntity.setIdTask(1L);
        taskEntity.setEvent(eventEntity);
        taskEntity.setStatus(TaskStatus.PENDING);

        taskDTO = new TaskDTO();
        taskDTO.setIdTask(1L);
        taskDTO.setIdEvent(1L);

        taskPostDTO = new TaskPostDTO();
        taskPostDTO.setIdEvent(1L);

        taskPutDTO = new TaskPutDTO();
    }

    @Test
    @DisplayName("TA-001/Should return all tasks")
    void testFindAll() {
        Page<TaskEntity> page = new PageImpl<>(List.of(taskEntity));
        given(taskRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).willReturn(taskDTO);

        Page<TaskDTO> result = taskService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("TA-002/Should find task by ID")
    void testFindById() {
        given(taskRepository.findById(1L)).willReturn(Optional.of(taskEntity));
        given(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).willReturn(taskDTO);

        Optional<TaskDTO> result = taskService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdTask());
    }

    @Test
    @DisplayName("TA-003/Should save new task")
    void testSaveTask() {
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(taskRepository.save(any(TaskEntity.class))).willReturn(taskEntity);
        given(modelMapper.map(any(TaskPostDTO.class), eq(TaskEntity.class))).willReturn(taskEntity);
        given(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).willReturn(taskDTO);

        TaskDTO result = taskService.save(taskPostDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("TA-004/Should update task")
    void testUpdateTask() {
        given(taskRepository.findById(1L)).willReturn(Optional.of(taskEntity));
        willDoNothing().given(modelMapper).map(any(TaskPutDTO.class), any(TaskEntity.class));
        given(taskRepository.save(any(TaskEntity.class))).willReturn(taskEntity);
        given(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).willReturn(taskDTO);

        TaskDTO result = taskService.update(1L, taskPutDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("TA-005/Should delete task")
    void testDeleteTask() {
        given(taskRepository.findById(1L)).willReturn(Optional.of(taskEntity));

        assertDoesNotThrow(() -> taskService.delete(1L));
        assertTrue(taskEntity.getSoftDelete());
    }

    @Test
    @DisplayName("TA-006/Should throw if task not found")
    void testTaskNotFound() {
        given(taskRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.delete(1L));
        assertThrows(EntityNotFoundException.class, () -> taskService.update(1L, taskPutDTO));
        assertThrows(EntityNotFoundException.class, () -> taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS));
    }

    @Test
    @DisplayName("TA-007/Should update task status")
    void testUpdateTaskStatus() {
        taskEntity.setStatus(TaskStatus.PENDING);
        given(taskRepository.findById(1L)).willReturn(Optional.of(taskEntity));
        given(taskRepository.save(any())).willReturn(taskEntity);
        given(modelMapper.map(any(), eq(TaskDTO.class))).willReturn(taskDTO);

        TaskDTO result = taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS);

        assertNotNull(result);
    }

    @Test
    @DisplayName("TA-008/Should throw on invalid status transition")
    void testInvalidStatusTransition() {
        taskEntity.setStatus(TaskStatus.COMPLETED);
        given(taskRepository.findById(1L)).willReturn(Optional.of(taskEntity));

        assertThrows(IllegalStateException.class, () ->
                taskService.updateTaskStatus(1L, TaskStatus.PENDING));
    }

    @Test
    @DisplayName("TA-009/Should get tasks from event")
    void testGetTasksFromEvent() {
        eventEntity.setTasks(List.of(taskEntity));
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(modelMapper.map(any(), eq(TaskDTO.class))).willReturn(taskDTO);

        List<TaskDTO> result = taskService.getTasksFromEvent(1L);

        assertEquals(1, result.size());
    }
}

