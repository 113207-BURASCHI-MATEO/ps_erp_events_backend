package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.notification.NotificationDTO;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.entities.ClientEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    void sendEmailToContacts(NotificationPostDTO notificationPostDTO);
    void sendEmailToClient(NotificationPostDTO notificationPostDTO, ClientEntity clientEntiy);

    List<NotificationDTO> getNotifications(Long idUser);

    void visualizeNotification(Long idNotification);

}
