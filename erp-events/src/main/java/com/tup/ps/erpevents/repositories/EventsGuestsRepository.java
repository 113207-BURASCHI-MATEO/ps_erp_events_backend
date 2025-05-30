package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.GuestEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsGuestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsGuestsRepository extends JpaRepository<EventsGuestsEntity, Long> {
    Optional<EventsGuestsEntity> findByEventAndGuest(EventEntity event, GuestEntity guest);
}
