package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.GuestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {

    Page<GuestEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Page<GuestEntity> findByFirstNameAndSoftDelete(String firstName, Pageable pageable, Boolean softDelete);

    Page<GuestEntity> findByLastNameAndSoftDelete(String lastName, Pageable pageable, Boolean softDelete);

    Page<GuestEntity> findAll(Specification<GuestEntity> spec, Pageable pageable);
}

