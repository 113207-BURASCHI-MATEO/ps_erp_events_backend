package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.guest.GuestAccessDTO;
import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public interface GuestService {

    Page<GuestDTO> findAll(Pageable pageable);

    Optional<GuestDTO> findById(Long id);

    GuestDTO save(GuestPostDTO guestDTO);

    GuestDTO update(Long id, GuestPutDTO guestDTO);

    void delete(Long id);

    Page<GuestDTO> findByFilters(Pageable pageable,
                                 String guestType,
                                 Boolean isActive,
                                 String searchValue,
                                 LocalDate creationStart,
                                 LocalDate creationEnd);

    List<GuestDTO> saveGuestsToEvent(List<GuestPostDTO> guestDTOList, Long idEvent);

    List<GuestDTO> getGuestFromEvent(Long idEvent);

    GuestDTO registerAccess(GuestAccessDTO guestAccessDTO);
}

