package com.tup.ps.erpevents.dtos.task;

import com.tup.ps.erpevents.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    @Schema(description = "ID de la tarea", example = "1")
    private Long idTask;

    @Schema(description = "Título de la tarea", example = "Instalar luces")
    private String title;

    @Schema(description = "Descripción de la tarea", example = "Instalación del sistema de iluminación del salón principal")
    private String description;

    @Schema(description = "Estado de la tarea", example = "PENDING")
    private TaskStatus status;

    @Schema(description = "ID del evento asociado", example = "2")
    private Long idEvent;
}
