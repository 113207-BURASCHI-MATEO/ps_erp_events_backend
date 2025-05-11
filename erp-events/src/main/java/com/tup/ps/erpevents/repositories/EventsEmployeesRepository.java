package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.intermediates.EventsEmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsEmployeesRepository extends JpaRepository<EventsEmployeesEntity, Long> {
}
