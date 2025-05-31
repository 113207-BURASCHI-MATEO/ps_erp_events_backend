package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.task.schedule.TimeScheduleDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePostDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePutDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.services.impl.TimeScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class TimeScheduleServiceImplTest {

    @Mock private TimeScheduleRepository timeScheduleRepository;
    @Mock private EventRepository eventRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks private TimeScheduleServiceImpl timeScheduleService;

    private TimeScheduleEntity timeSchedule;
    private TimeScheduleDTO timeScheduleDTO;
    private EventEntity event;
    private TaskEntity task;

    @BeforeEach
    void setUp() {
        timeSchedule = new TimeScheduleEntity();
        timeSchedule.setIdTimeSchedule(1L);
        timeSchedule.setTitle("Schedule Title");
        timeSchedule.setDescription("Description");
        timeSchedule.setScheduledTasks(new ArrayList<>());

        event = new EventEntity();
        event.setIdEvent(1L);

        task = new TaskEntity();
        task.setIdTask(1L);
        task.setEvent(event);

        timeScheduleDTO = new TimeScheduleDTO();
        timeScheduleDTO.setIdTimeSchedule(1L);
        timeScheduleDTO.setTitle("Schedule Title");
        timeScheduleDTO.setDescription("Description");
        timeScheduleDTO.setIdEvent(1L);
    }

    @Test
    @DisplayName("TS-001/Should save a time schedule with scheduled tasks")
    void testSaveTimeSchedule() {
        TimeSchedulePostDTO postDTO = new TimeSchedulePostDTO();
        postDTO.setIdEvent(1L);
        postDTO.setTitle("Schedule Title");
        postDTO.setDescription("Description");
        postDTO.setScheduledTasks(Map.of(LocalDateTime.now(), 1L));

        given(eventRepository.findById(1L)).willReturn(Optional.of(event));
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(timeScheduleRepository.save(any(TimeScheduleEntity.class))).willReturn(timeSchedule);
        given(eventRepository.save(any())).willReturn(event);

        TimeScheduleDTO result = timeScheduleService.save(postDTO);

        assertNotNull(result);
        assertEquals("Schedule Title", result.getTitle());
    }

    @Test
    @DisplayName("TS-002/Should update an existing time schedule")
    void testUpdateTimeSchedule() {
        TimeSchedulePutDTO putDTO = new TimeSchedulePutDTO();
        putDTO.setTitle("Updated Title");
        putDTO.setDescription("Updated Description");
        putDTO.setScheduledTasks(Map.of(LocalDateTime.now(), 1L));

        given(timeScheduleRepository.findById(1L)).willReturn(Optional.of(timeSchedule));
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(timeScheduleRepository.save(any(TimeScheduleEntity.class))).willReturn(timeSchedule);

        TimeScheduleDTO result = timeScheduleService.update(1L, putDTO);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    @DisplayName("TS-003/Should delete a time schedule")
    void testDeleteTimeSchedule() {
        timeSchedule.setSoftDelete(false);
        given(timeScheduleRepository.findById(1L)).willReturn(Optional.of(timeSchedule));

        assertDoesNotThrow(() -> timeScheduleService.delete(1L));
    }

    @Test
    @DisplayName("TS-004/Should throw if deleting an already deleted time schedule")
    void testDeleteAlreadyDeletedTimeSchedule() {
        timeSchedule.setSoftDelete(true);
        given(timeScheduleRepository.findById(1L)).willReturn(Optional.of(timeSchedule));

        assertThrows(IllegalStateException.class, () -> timeScheduleService.delete(1L));
    }

    @Test
    @DisplayName("TS-005/Should return time schedule by ID")
    void testFindById() {
        given(timeScheduleRepository.findById(1L)).willReturn(Optional.of(timeSchedule));

        Optional<TimeScheduleDTO> result = timeScheduleService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Schedule Title", result.get().getTitle());
    }

    @Test
    @DisplayName("TS-006/Should return all time schedules")
    void testFindAll() {
        Page<TimeScheduleEntity> page = new PageImpl<>(List.of(timeSchedule));
        given(timeScheduleRepository.findAll(Pageable.unpaged())).willReturn(page);

        Page<TimeScheduleDTO> result = timeScheduleService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }
}

