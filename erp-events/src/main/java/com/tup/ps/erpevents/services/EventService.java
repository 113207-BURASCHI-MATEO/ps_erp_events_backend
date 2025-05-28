package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public interface EventService {

    Page<EventDTO> findAll(Pageable pageable);

    Optional<EventDTO> findById(Long id);

    EventDTO save(EventPostDTO eventPostDTO);

    EventDTO update(Long id, EventPutDTO eventPutDTO);


    void delete(Long id);

    EventDTO eventStatus(Long id, EventStatus eventStatus);

    Page<EventDTO> findByFilters(Pageable pageable,
                                 String eventType,
                                 String status,
                                 Boolean isActive,
                                 String searchValue,
                                 LocalDate dateStart,
                                 LocalDate dateEnd);

}

