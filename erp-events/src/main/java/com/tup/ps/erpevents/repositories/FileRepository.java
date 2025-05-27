package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {

    @NotNull Page<FileEntity> findAll(@NotNull Pageable pageable);
    Optional<FileEntity> findByIdFileAndSupplier(@NotNull Long id, SupplierEntity supplier);
    Optional<FileEntity> findByIdFileAndClient(@NotNull Long id, ClientEntity client);
    Optional<FileEntity> findByIdFileAndEmployee(@NotNull Long id, EmployeeEntity employee);
    Page<FileEntity> findAll(Specification<FileEntity> spec, Pageable pageable);
}
