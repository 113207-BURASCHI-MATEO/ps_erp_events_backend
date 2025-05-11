package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.ClientEntity;
import com.tup.ps.erpevents.enums.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByEmail(String email);

    Optional<ClientEntity> findByAliasCbu(String aliasOrCbu);

    Boolean existsByEmail(String email);

    Boolean existsByAliasCbu(String aliasOrCbu);

    @EntityGraph(attributePaths = "events")
    Page<ClientEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Page<ClientEntity> findByFirstNameAndSoftDelete(String firstName, Pageable pageable, Boolean softDelete);

    Page<ClientEntity> findByLastNameAndSoftDelete(String lastName, Pageable pageable, Boolean softDelete);

    Page<ClientEntity> findByFirstNameAndLastNameAndSoftDelete(String firstName, String lastName, Pageable pageable, Boolean softDelete);

    @EntityGraph(attributePaths = "events")
    Page<ClientEntity> findAll(Specification<ClientEntity> spec, Pageable pageable);

    Optional<ClientEntity> findBySoftDeleteFalseAndDocumentTypeAndDocumentNumber(DocumentType documentType, String documentNumber);
}

