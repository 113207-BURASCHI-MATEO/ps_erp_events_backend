package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    void sendEmailToContacts(NotificationPostDTO notificationPostDTO);

}
