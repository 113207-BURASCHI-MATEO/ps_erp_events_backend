package com.tup.ps.erpevents.dtos.task.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSchedulePostDTO {

    @NotNull
    @Schema(description = "ID del evento al que pertenece el cronograma", example = "3")
    private Long idEvent;

    @NotNull
    @Schema(description = "Titulo del cronograma", example = "Timing Festival")
    private String title;

    @Schema(description = "Descripcion del cronograma", example = "Boda")
    private String description;

    @NotEmpty
    @Schema(description = "Mapa de horarios con los IDs de las tareas", example = "{\"2025-06-01T10:00:00\": 12}")
    private Map<LocalDateTime, Long> scheduledTasks = new HashMap<>();
}
