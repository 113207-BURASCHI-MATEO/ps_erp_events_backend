package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.notification.NotificationDTO;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Obtener todas las notificaciones de un usuario")
    @GetMapping("{idUser}")
    public ResponseEntity<?> getNotifications(@PathVariable Long idUser) {
        List<NotificationDTO> notifications =
                notificationService.getNotifications(idUser);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("{idNotification}")
    public ResponseEntity<Void> visualizeNotification(@PathVariable Long idNotification) {
        notificationService.visualizeNotification(idNotification);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Enviar notificaciones a un contacto")
    @PostMapping("/send-to-contacts")
    public ResponseEntity<Void> sendEmailToContacts(@RequestBody NotificationPostDTO
                                                            notificationPostDTO) {
        notificationService.sendEmailToContacts(notificationPostDTO);
        return ResponseEntity.ok().build();
    }

}
