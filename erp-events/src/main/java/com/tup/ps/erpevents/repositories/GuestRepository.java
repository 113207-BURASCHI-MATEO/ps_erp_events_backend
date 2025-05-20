package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.GuestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {

    Page<GuestEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Optional<GuestEntity> findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);

    Page<GuestEntity> findByLastNameAndSoftDelete(String lastName, Pageable pageable, Boolean softDelete);

    Page<GuestEntity> findAll(Specification<GuestEntity> spec, Pageable pageable);
}

