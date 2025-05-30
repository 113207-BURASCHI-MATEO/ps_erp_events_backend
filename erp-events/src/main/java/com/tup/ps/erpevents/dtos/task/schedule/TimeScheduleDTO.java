package com.tup.ps.erpevents.dtos.task.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeScheduleDTO {
    @Schema(description = "ID del cronograma", example = "1")
    private Long idTimeSchedule;

    @Schema(description = "Titulo del cronograma", example = "Timing Festival")
    private String title;

    @Schema(description = "Descripcion del cronograma", example = "Boda")
    private String description;

    @Schema(description = "ID del evento asociado", example = "3")
    private Long idEvent;

    @Schema(description = "Mapa de horarios con los IDs de las tareas", example = "{\"2025-06-01T10:00:00\": 12}")
    private Map<LocalDateTime, Long> scheduledTasks = new HashMap<>();
}
