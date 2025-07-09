package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPostDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPutDTO;
import com.tup.ps.erpevents.entities.AccountEntity;
import com.tup.ps.erpevents.entities.ConceptEntity;
import com.tup.ps.erpevents.entities.FileEntity;
import com.tup.ps.erpevents.enums.AccountingConcept;
import com.tup.ps.erpevents.repositories.AccountRespository;
import com.tup.ps.erpevents.repositories.ConceptRespository;
import com.tup.ps.erpevents.repositories.FileRepository;
import com.tup.ps.erpevents.services.ConceptService;
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

@Service
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {

    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConceptRespository conceptRespository;

    @Autowired
    private AccountRespository accountRespository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Page<ConceptDTO> findAll(Pageable pageable) {
        return conceptRespository.findAll(pageable)
                .map(concept -> modelMapper.map(concept, ConceptDTO.class));
    }

    @Override
    public Optional<ConceptDTO> findById(Long id) {
        return conceptRespository.findById(id)
                .map(concept -> modelMapper.map(concept, ConceptDTO.class));
    }

    @Override
    @Transactional
    public ConceptDTO save(ConceptPostDTO conceptPostDTO) {
        AccountEntity account = accountRespository.findById(conceptPostDTO.getIdAccount())
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));
        if (conceptPostDTO.getIdFile() != null) {
            FileEntity fileEntity = fileRepository.findById(conceptPostDTO.getIdFile())
                    .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado"));
        }
        ConceptEntity concept = modelMapper.map(conceptPostDTO, ConceptEntity.class);
        concept.setAccount(account);
        if (conceptPostDTO.getConcept().equals(AccountingConcept.PAYMENT)) {
            concept.setAmount(conceptPostDTO.getAmount().negate());
        }
        account.setBalance(account.getBalance().add(concept.getAmount()));
        accountRespository.save(account);
        return modelMapper.map(conceptRespository.save(concept), ConceptDTO.class);
    }

    @Override
    @Transactional
    public ConceptDTO update(Long id, ConceptPutDTO conceptPutDTO) {
        AccountEntity account = accountRespository.findById(conceptPutDTO.getIdAccount())
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));

        if (conceptPutDTO.getIdFile() != null) {
            FileEntity fileEntity = fileRepository.findById(conceptPutDTO.getIdFile())
                    .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado"));
        }

        ConceptEntity concept = conceptRespository.findById(conceptPutDTO.getIdConcept())
                        .orElseThrow(()-> new EntityNotFoundException("Cuenta no encontrada"));

        modelMapper.map(conceptPutDTO, ConceptEntity.class);
        concept.setAccount(account);
        if (conceptPutDTO.getConcept().equals(AccountingConcept.PAYMENT)) {
            concept.setAmount(conceptPutDTO.getAmount().negate());
        }
        account.setBalance(account.getBalance().add(concept.getAmount()));
        accountRespository.save(account);

        return modelMapper.map(conceptRespository.save(concept), ConceptDTO.class);
    }

    @Override
    public void delete(Long id) {
        ConceptEntity concept = conceptRespository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("No concept found with id " + id));
        concept.setSoftDelete(true);
        conceptRespository.save(concept);
    }

    @Override
    public Page<ConceptDTO> findByFilters(Pageable pageable,
                                          AccountingConcept concept,
                                          Long idAccount,
                                          String searchValue,
                                          LocalDate dateStart,
                                          LocalDate dateEnd) {
        return null;
    }

    @Override
    public Page<ConceptDTO> findByAccountId(Pageable pageable, Long idAccount) {
        AccountEntity account = accountRespository.findById(idAccount)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));
        return conceptRespository.findAllByAccount(pageable, account)
                .map(concept -> modelMapper.map(concept, ConceptDTO.class));
    }
}
