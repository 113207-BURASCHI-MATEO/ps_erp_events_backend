package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.intermediates.EventsSuppliersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsSuppliersRepository extends JpaRepository<EventsSuppliersEntity, Long> {
}
