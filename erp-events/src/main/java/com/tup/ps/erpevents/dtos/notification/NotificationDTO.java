package com.tup.ps.erpevents.dtos.notification;

import com.tup.ps.erpevents.enums.StatusSend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    @Schema(description = "ID de la notificación", example = "1")
    private Long idNotification;

    @Schema(description = "Correo electrónico o destinatario de la notificación", example = "usuario@example.com")
    private String recipient;

    @Schema(description = "ID del contacto asociado a la notificación", example = "10")
    private Long idContact;

    @Schema(description = "Asunto de la notificación", example = "Bienvenido a ERP Eventos")
    private String subject;

    @Schema(description = "ID de la plantilla utilizada para la notificación", example = "5")
    private Long idTemplate;

    @Schema(description = "Nombre de la plantilla utilizada", example = "Welcome Template")
    private String templateName;

    @Schema(description = "Fecha de envio", example = "10-05-2025")
    private LocalDateTime dateSend;

    @Schema(description = "Estado de la notificacion", example = "SENT")
    private StatusSend statusSend;

    @Schema(description = "Cuerpo del mensaje de la notificación en formato HTML o texto", example = "<h1>Bienvenido</h1><p>Gracias por registrarte.</p>")
    private String body;

    @Schema(description = "Lista de variables personalizadas para la plantilla")
    private List<KeyValueCustomPair> variables = new ArrayList<>();
}
