package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.task.schedule.TimeScheduleDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePostDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePutDTO;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.ScheduledTaskEntity;
import com.tup.ps.erpevents.entities.TaskEntity;
import com.tup.ps.erpevents.entities.TimeScheduleEntity;
import com.tup.ps.erpevents.repositories.EventRepository;
import com.tup.ps.erpevents.repositories.TaskRepository;
import com.tup.ps.erpevents.repositories.TimeScheduleRepository;
import com.tup.ps.erpevents.services.TimeScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeScheduleServiceImpl  implements TimeScheduleService {

    @Autowired
    private TimeScheduleRepository timeScheduleRepository;

    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public Page<TimeScheduleDTO> findAll(Pageable pageable) {
        return timeScheduleRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    public Optional<TimeScheduleDTO> findById(Long id) {
        return timeScheduleRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public TimeScheduleDTO save(TimeSchedulePostDTO dto) {
        EventEntity event = eventRepository.findById(dto.getIdEvent())
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + dto.getIdEvent()));

        TimeScheduleEntity timeSchedule = new TimeScheduleEntity();

        List<ScheduledTaskEntity> scheduledTasks = mapToScheduledTasks(dto.getScheduledTasks(), timeSchedule);

        timeSchedule.setScheduledTasks(scheduledTasks);
        timeSchedule.setTitle(dto.getTitle());
        timeSchedule.setDescription(dto.getDescription());

        TimeScheduleEntity saved = timeScheduleRepository.save(timeSchedule);


        event.setTimeSchedule(saved);
        eventRepository.save(event);

        return convertToDto(saved);
    }


    @Override
    public TimeScheduleDTO update(Long id, TimeSchedulePutDTO dto) {
        TimeScheduleEntity timeSchedule = timeScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cronograma no encontrado con ID: " + id));

        timeSchedule.getScheduledTasks().clear();

        List<ScheduledTaskEntity> scheduledTasks = mapToScheduledTasks(dto.getScheduledTasks(), timeSchedule);

        timeSchedule.getScheduledTasks().addAll(scheduledTasks);
        timeSchedule.setTitle(dto.getTitle());
        timeSchedule.setDescription(dto.getDescription());

        TimeScheduleEntity updated = timeScheduleRepository.save(timeSchedule);
        return convertToDto(updated);
    }


    @Override
    public void delete(Long id) {
        TimeScheduleEntity entity = timeScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TimeSchedule no encontrado con ID: " + id));

        if (Boolean.TRUE.equals(entity.getSoftDelete())) {
            throw new IllegalStateException("El cronograma ya fue eliminado.");
        }

        entity.setSoftDelete(true);
        timeScheduleRepository.save(entity);
    }


    private TimeScheduleDTO convertToDto(TimeScheduleEntity entity) {
        TimeScheduleDTO dto = new TimeScheduleDTO();
        dto.setIdTimeSchedule(entity.getIdTimeSchedule());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());

        if (entity.getEvent() != null) {
            dto.setIdEvent(entity.getEvent().getIdEvent());
        }

        dto.setScheduledTasks(
                entity.getScheduledTasks().stream()
                        .collect(Collectors.toMap(
                                ScheduledTaskEntity::getScheduledTime,
                                st -> st.getTask().getIdTask()
                        ))
        );

        return dto;
    }

    private List<ScheduledTaskEntity> mapToScheduledTasks(
            Map<LocalDateTime, Long> scheduledTasksDto,
            TimeScheduleEntity timeSchedule
    ) {
        return scheduledTasksDto.entrySet().stream()
                .map(entry -> {
                    TaskEntity task = taskRepository.findById(entry.getValue())
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Tarea no encontrada con ID: " + entry.getValue()));

                    ScheduledTaskEntity scheduled = new ScheduledTaskEntity();
                    scheduled.setScheduledTime(entry.getKey());
                    scheduled.setTask(task);
                    scheduled.setTimeSchedule(timeSchedule);
                    return scheduled;
                }).toList();
    }


}
