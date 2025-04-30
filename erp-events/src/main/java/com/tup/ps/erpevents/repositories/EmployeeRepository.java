package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity>
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmail(String email);

    Optional<EmployeeEntity> findByCuit(String cuit);

    Boolean existsByEmail(String email);

    Boolean existsByCuit(String cuit);

    Page<EmployeeEntity> findAllBySoftDelete(Boolean softDelete, Pageable pageable);

    Optional<EmployeeEntity> findByDocumentNumber(String documentNumber);

    Page<EmployeeEntity> findByFirstNameAndSoftDelete(String firstName, Pageable pageable, Boolean softDelete);

    Page<EmployeeEntity> findByLastNameAndSoftDelete(String lastName, Pageable pageable, Boolean softDelete);

    Page<EmployeeEntity> findByFirstNameAndLastNameAndSoftDelete(String firstName, String lastName,
                                                                 Pageable pageable, Boolean softDelete);
    Page<EmployeeEntity> findByDocumentTypeAndSoftDelete(String documentType, Boolean softDelete, Pageable pageable);

    Page<EmployeeEntity> findAll(Specification<EmployeeEntity> spec, Pageable pageable);
}
