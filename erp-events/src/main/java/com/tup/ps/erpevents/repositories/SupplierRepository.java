package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.SupplierEntity;
import com.tup.ps.erpevents.enums.SupplierType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> findByEmail(String email);

    Optional<SupplierEntity> findByCuit(String cuit);

    Boolean existsByEmail(String email);

    Boolean existsByCuit(String cuit);

    Page<SupplierEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Optional<SupplierEntity> findByPhoneNumber(String phoneNumber);

    Page<SupplierEntity> findByNameAndSoftDelete(String name, Pageable pageable, Boolean softDelete);

    Page<SupplierEntity> findBySupplierTypeAndSoftDelete(SupplierType type, Boolean softDelete, Pageable pageable);

    Page<SupplierEntity> findAll(Specification<SupplierEntity> spec, Pageable pageable);
}
