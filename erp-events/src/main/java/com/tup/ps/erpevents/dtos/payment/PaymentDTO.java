package com.tup.ps.erpevents.dtos.payment;

import com.tup.ps.erpevents.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PaymentDTO", description = "DTO para entidad Payment")
public class PaymentDTO {

    @Schema(description = "ID del pago", example = "1")
    private Long idPayment;

    @Schema(description = "Fecha del pago", example = "2025-05-23T10:00:00")
    private LocalDateTime paymentDate;

    @Schema(description = "ID del cliente asociado", example = "1")
    private Long idClient;

    @Schema(description = "Importe del pago", example = "15000.00")
    private Double amount;

    @Schema(description = "Detalle del pago", example = "Pago por evento corporativo")
    private String detail;

    @Schema(description = "Estado del pago", example = "COBRADO")
    private PaymentStatus status;

    @Schema(description = "URL del pago", example = "http://mp.com")
    private String reviewNote;

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;

}

