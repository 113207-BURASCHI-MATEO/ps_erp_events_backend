package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.AccountEntity;
import com.tup.ps.erpevents.entities.ConceptEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptRespository extends JpaRepository<ConceptEntity, Long> {
    Page<ConceptEntity> findAllByAccount(Pageable pageable, AccountEntity account);
}
