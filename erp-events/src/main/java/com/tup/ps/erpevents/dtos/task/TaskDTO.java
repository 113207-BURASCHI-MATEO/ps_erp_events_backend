package com.tup.ps.erpevents.dtos.task;

import com.tup.ps.erpevents.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;
}
