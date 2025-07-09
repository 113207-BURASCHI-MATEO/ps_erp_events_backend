package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPostDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPutDTO;
import com.tup.ps.erpevents.enums.AccountingConcept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface ConceptService {

    Page<ConceptDTO> findAll(Pageable pageable);

    Optional<ConceptDTO> findById(Long id);

    ConceptDTO save(ConceptPostDTO conceptPostDTO);

    ConceptDTO update(Long id, ConceptPutDTO conceptPutDTO);

    void delete(Long id);

    Page<ConceptDTO> findByFilters(Pageable pageable,
                                   AccountingConcept concept,
                                   Long idAccount,
                                   String searchValue,
                                   LocalDate dateStart,
                                   LocalDate dateEnd);

    Page<ConceptDTO> findByAccountId(Pageable pageable, Long idAccount);

}
