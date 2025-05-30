package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.ClientEntity;
import com.tup.ps.erpevents.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByClient(ClientEntity clientEntity);
    Page<PaymentEntity> findAll(Specification<PaymentEntity> spec, Pageable pageable);

    List<PaymentEntity> findByClientOrderByCreationDateDesc(ClientEntity clientEntity);
}
