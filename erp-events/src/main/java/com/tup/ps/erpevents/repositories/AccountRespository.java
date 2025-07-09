package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.AccountEntity;
import com.tup.ps.erpevents.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRespository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByEvent(EventEntity event);
}
