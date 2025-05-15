package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.services.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@Validated
@AllArgsConstructor
@Tag(name = "Notificaciones", description = "Gestión de notificaciones del sistema")
public class NotificationController {

    /**
     * Service to manage the email petitions.
     */
    @Autowired
    private NotificationService notificationService;


    @PostMapping("/send-to-contacts")
    public ResponseEntity<Void> sendEmailToContacts(@RequestBody NotificationPostDTO
                                                            notificationPostDTO) {
        notificationService.sendEmailToContacts(notificationPostDTO);
        return ResponseEntity.ok().build();
    }
}
