package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public interface ClientService {

    Page<ClientDTO> findAll(Pageable pageable);

    Optional<ClientDTO> findById(Long id);

    ClientDTO save(ClientPostDTO clientDTO);

    ClientDTO update(Long id, ClientPutDTO clientDTO);

    void delete(Long id);

    Page<ClientDTO> findByFilters(Pageable pageable,
                                  String documentType,
                                  Boolean isActive,
                                  String searchValue,
                                  LocalDate creationStart,
                                  LocalDate creationEnd);

    Optional<ClientDTO> findByDocumentTypeAndDocumentNumber(DocumentType documentType, String documentNumber);
}

