package com.tup.ps.erpevents.dtos.event.relations;

import com.tup.ps.erpevents.enums.AmountStatus;
import com.tup.ps.erpevents.enums.GuestType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Fecha de creación", example = "2025-05-10T14:30:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización", example = "2025-05-15T10:00:00")
    private LocalDateTime updateDate;
}
