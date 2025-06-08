package com.tup.ps.erpevents.dtos.event;

import com.tup.ps.erpevents.dtos.event.account.AccountDTO;
import com.tup.ps.erpevents.dtos.event.relations.EventsEmployeesDTO;
import com.tup.ps.erpevents.dtos.event.relations.EventsSuppliersDTO;
import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.enums.EventStatus;
import com.tup.ps.erpevents.enums.EventType;
import com.tup.ps.erpevents.dtos.employee.EmployeeDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierDTO;
import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.dtos.location.LocationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    @Schema(description = "ID del evento", example = "1")
    private Long idEvent;

    @Schema(description = "Título del evento", example = "Fiesta de Fin de Año")
    private String title;

    @Schema(description = "Descripción del evento", example = "Evento anual de la empresa")
    private String description;

    @Schema(description = "Tipo de evento", example = "CORPORATE")
    private EventType eventType;

    @Schema(description = "Fecha de inicio del evento", example = "2025-12-31T20:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Fecha de finalización del evento", example = "2026-01-01T03:00:00")
    private LocalDateTime endDate;

    @Schema(description = "Estado actual del evento", example = "CONFIRMED")
    private EventStatus status;

    @Schema(description = "Indica si fue eliminado lógicamente", example = "false")
    private Boolean softDelete;

    @Schema(description = "Fecha de creación", example = "2025-05-01T13:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización", example = "2025-05-02T09:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Cliente asociado al evento")
    private ClientDTO client;

    @Schema(description = "Ubicación del evento")
    private LocationDTO location;

    @Schema(description = "Lista de ids de empleados asignados al evento")
    private List<Long> employeesIds;

    @Schema(description = "Relaciones de empleados con información de pagos y estados")
    private List<EventsEmployeesDTO> employees;

    @Schema(description = "Lista de ids de proveedores asignados al evento")
    private List<Long> suppliersIds;

    @Schema(description = "Relaciones de proveedores con información de pagos y estados")
    private List<EventsSuppliersDTO> suppliers;

    @Schema(description = "Lista de invitados al evento")
    private List<Long> guests;

    @Schema(description = "Lista de tareas asignadas al evento")
    private List<TaskDTO> tasks;

    @Schema(description = "Presupuesto del evento")
    private AccountDTO account;
}

