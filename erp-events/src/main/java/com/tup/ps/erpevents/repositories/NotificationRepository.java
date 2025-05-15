package com.tup.ps.erpevents.repositories;

import com.tup.ps.erpevents.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>,
        JpaSpecificationExecutor<NotificationEntity> {

    List<NotificationEntity> findByDateSendBetween(LocalDateTime from, LocalDateTime until);

    List<NotificationEntity> findByRecipient(String recipient);
}
