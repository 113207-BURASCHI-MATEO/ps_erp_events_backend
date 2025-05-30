package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.task.schedule.TimeScheduleDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePostDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TimeScheduleService {

    Page<TimeScheduleDTO> findAll(Pageable pageable);

    Optional<TimeScheduleDTO> findById(Long id);

    TimeScheduleDTO save(TimeSchedulePostDTO dto);

    TimeScheduleDTO update(Long id, TimeSchedulePutDTO dto);

    void delete(Long id);
}
