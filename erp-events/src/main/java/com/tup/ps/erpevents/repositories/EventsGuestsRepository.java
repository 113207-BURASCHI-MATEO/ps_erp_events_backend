package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.intermediates.EventsGuestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsGuestsRepository extends JpaRepository<EventsGuestsEntity, Long> {
}
