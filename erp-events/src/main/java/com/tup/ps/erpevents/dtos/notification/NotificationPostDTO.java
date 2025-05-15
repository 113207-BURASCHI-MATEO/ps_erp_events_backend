package com.tup.ps.erpevents.dtos.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPostDTO {

    @Schema(description = "Identificador del template", example = "1")
    private Long idTemplate;
    @Schema(description = "Lista de contactos", example = "[1, 2]")
    private List<Long> contactIds = new ArrayList<>();
    @Schema(description = "Mail de destino", example = "usuario@mail.com")
    private String subject;
    @Schema(description = "Variables", example = "evento: XYZ")
    private List<KeyValueCustomPair> variables = new ArrayList<>();
    @Schema(description = "Tipo de notificacion", example = "XYZ")
    @Nullable
    private String notificationType;
}
