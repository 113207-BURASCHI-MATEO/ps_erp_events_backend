package com.tup.ps.erpevents.dtos.payment;

import com.tup.ps.erpevents.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPostDTO {
    @Schema(description = "Fecha en que se realizó el pago", example = "2025-06-15", required = true)
    private LocalDateTime paymentDate;

    @Schema(description = "ID del cliente asociado al pago", example = "1", required = true)
    private Long idClient;

    @Schema(description = "Importe total del pago", example = "15000.50", required = true)
    private BigDecimal amount;

    @Schema(description = "Detalle o descripción del pago", example = "Pago parcial por evento", required = false)
    private String detail;

    @Schema(description = "Estado del pago", example = "COBRADO", required = true)
    private PaymentStatus status;
}
