package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Page<TaskEntity> findAll(Specification<TaskEntity> spec, Pageable pageable);
}