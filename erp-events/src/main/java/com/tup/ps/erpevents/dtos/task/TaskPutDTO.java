package com.tup.ps.erpevents.dtos.task;

import com.tup.ps.erpevents.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPutDTO {

    @NotNull(message = "El ID del tarea es obligatorio")
    @Schema(description = "ID de la tarea", example = "1")
    private Long idTask;

    @NotBlank
    @Schema(description = "Título de la tarea", example = "Instalar luces")
    private String title;

    @NotBlank
    @Schema(description = "Descripción de la tarea", example = "Instalación del sistema de iluminación del salón principal")
    private String description;

    @NotNull
    @Schema(description = "Estado de la tarea", example = "PENDING")
    private TaskStatus status;

    @NotNull
    @Schema(description = "ID del evento asociado", example = "2")
    private Long idEvent;
}

