package com.tup.ps.erpevents.dtos.event.relations;

import com.tup.ps.erpevents.enums.AccessType;
import com.tup.ps.erpevents.enums.AmountStatus;
import com.tup.ps.erpevents.enums.GuestType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsGuestsDTO {

    @Schema(description = "ID de la relación", example = "1")
    private Long idRelation;

    @Schema(description = "ID del evento", example = "5")
    private Long idEvent;

    @Schema(description = "ID del invitado", example = "8")
    private Long idGuest;

    @Schema(description = "Tipo de invitado", example = "PENDING")
    private GuestType type;

    @Schema(description = "Tipo de Acceso", example = "ENTRY")
    private AccessType accessType;

    @Schema(description = "Fecha de Acceso", example = "2025-05-10T14:30:00")
    private LocalDateTime actionDate;

    @Schema(description = "Marcador de demora", example = "true")
    private Boolean isLate;

    @Schema(description = "Sector del evento", example = "true")
    private String sector;

    @Schema(description = "Fila o mesa del evento", example = "true")
    private String rowTable;

    @Schema(description = "Asiento del evento", example = "true")
    private Integer seat;

    @Schema(description = "Restriccion alimentaria del invitado", example = "2")
    private Boolean foodRestriction;

    @Schema(description = "Descripcion de la restriccion alimentaria del invitado", example = "2")
    private String foodDescription;

    @Schema(description = "Fecha de creación", example = "2025-05-10T14:30:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización", example = "2025-05-15T10:00:00")
    private LocalDateTime updateDate;
}
