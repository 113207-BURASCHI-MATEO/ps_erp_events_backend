package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @EntityGraph(attributePaths = {"client", "location"})
    Page<EventEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "location"})
    Page<EventEntity> findAll(Specification<EventEntity> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "location"})
    Optional<EventEntity> findByIdEventAndSoftDeleteFalse(Long id);
}
