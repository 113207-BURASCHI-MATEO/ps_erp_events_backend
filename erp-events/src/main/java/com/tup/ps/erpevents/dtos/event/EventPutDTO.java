package com.tup.ps.erpevents.dtos.event;

import com.tup.ps.erpevents.enums.EventStatus;
import com.tup.ps.erpevents.enums.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPutDTO {

    @NotNull(message = "El ID del evento es obligatorio")
    @Schema(description = "ID del evento", example = "1")
    private Long idEvent;

    @NotBlank
    @Schema(description = "Título del evento", example = "Fiesta actualizada")
    private String title;

    @NotBlank
    @Schema(description = "Descripción del evento", example = "Descripción actualizada")
    private String description;

    @NotNull
    @Schema(description = "Tipo de evento", example = "ENTERTAINMENT")
    private EventType eventType;

    @NotNull
    @Schema(description = "Fecha y hora de inicio del evento", example = "2025-12-31T20:00:00")
    private LocalDateTime startDate;

    @NotNull
    @Schema(description = "Fecha y hora de finalización del evento", example = "2026-01-01T03:00:00")
    private LocalDateTime endDate;

    @NotNull
    @Schema(description = "Estado del evento", example = "IN_PROGRESS")
    private EventStatus status;

    /*@NotNull
    @Schema(description = "ID del cliente organizador del evento", example = "1")
    private Long idClient;*/

    /*@NotNull
    @Schema(description = "ID de la ubicación del evento", example = "2")
    private Long locationId;*/

    @Schema(description = "Lista de IDs de empleados asignados al evento", example = "[1, 2]")
    private List<Long> employeeIds;

    @Schema(description = "Lista de IDs de proveedores asignados al evento", example = "[1, 3]")
    private List<Long> supplierIds;

    @Schema(description = "Lista de IDs de invitados al evento", example = "[2, 4, 5]")
    private List<Long> guestIds;
}

