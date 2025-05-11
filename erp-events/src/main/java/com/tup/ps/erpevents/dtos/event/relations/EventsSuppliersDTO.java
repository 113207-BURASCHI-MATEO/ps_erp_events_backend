package com.tup.ps.erpevents.dtos.event.relations;

import com.tup.ps.erpevents.enums.AmountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsSuppliersDTO {

    @Schema(description = "ID de la relación", example = "1")
    private Long idRelation;

    @Schema(description = "ID del evento", example = "5")
    private Long idEvent;

    @Schema(description = "ID del proveedor", example = "8")
    private Long idSupplier;

    @Schema(description = "Estado del pago", example = "PENDING")
    private AmountStatus status;

    @Schema(description = "Monto asignado", example = "2000.00")
    private Double amount;

    @Schema(description = "Saldo restante", example = "750.00")
    private Double balance;

    @Schema(description = "Método de pago", example = "EFECTIVO")
    private String payment;

    @Schema(description = "Fecha de creación", example = "2025-05-10T14:30:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización", example = "2025-05-15T10:00:00")
    private LocalDateTime updateDate;
}
