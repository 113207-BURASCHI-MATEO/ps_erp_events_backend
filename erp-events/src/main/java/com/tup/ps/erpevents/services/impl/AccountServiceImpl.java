package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.event.account.AccountDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.entities.AccountEntity;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.repositories.AccountRespository;
import com.tup.ps.erpevents.repositories.EventRepository;
import com.tup.ps.erpevents.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRespository accountRespository;

    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Page<AccountDTO> findAll(Pageable pageable) {
        return accountRespository.findAll(pageable)
                .map(account -> {
                    AccountDTO dto = modelMapper.map(account, AccountDTO.class);
                    dto.setConcepts(account.getConcepts().stream()
                            .map(concept ->
                                    modelMapper.map(concept, ConceptDTO.class)).collect(Collectors.toList()));
                    return dto;
                });
    }

    @Override
    @Transactional
    public Optional<AccountDTO> findById(Long id) {
        return accountRespository.findById(id)
                .map(account -> {
                    AccountDTO dto = modelMapper.map(account, AccountDTO.class);
                    dto.setConcepts(account.getConcepts().stream()
                            .map(concept ->
                                    modelMapper.map(concept, ConceptDTO.class)).collect(Collectors.toList()));
                    return dto;
                });
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO update(Long id, AccountDTO accountDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        AccountEntity account = accountRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));
        account.setSoftDelete(true);
        accountRespository.save(account);
    }

    @Override
    public Page<AccountDTO> findByFilters(Pageable pageable,
                                          Boolean isActive,
                                          String searchValue,
                                          LocalDate creationStart,
                                          LocalDate creationEnd) {
        return null;
    }

    @Override
    public AccountDTO findByEventId(Pageable pageable, Long idEvent) {
        EventEntity event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
        AccountEntity account = accountRespository.findByEvent(event)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setConcepts(account.getConcepts().stream()
        .map(concept -> modelMapper.map(concept, ConceptDTO.class)).toList());
        return accountDTO;
    }
}
