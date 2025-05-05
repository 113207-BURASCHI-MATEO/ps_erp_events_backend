package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Page<LocationEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Page<LocationEntity> findAll(Specification<LocationEntity> spec, Pageable pageable);
}

