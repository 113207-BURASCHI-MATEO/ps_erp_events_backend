package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.TimeScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeScheduleRepository extends JpaRepository<TimeScheduleEntity, Long> {
}
