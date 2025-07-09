package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.event.account.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface AccountService {
    Page<AccountDTO> findAll(Pageable pageable);

    Optional<AccountDTO> findById(Long id);

    AccountDTO save(AccountDTO accountDTO);

    AccountDTO update(Long id, AccountDTO accountDTO);

    void delete(Long id);

    Page<AccountDTO> findByFilters(Pageable pageable,
                                   Boolean isActive,
                                   String searchValue,
                                   LocalDate creationStart,
                                   LocalDate creationEnd);

    AccountDTO findByEventId(Pageable pageable, Long idEvent);
}
