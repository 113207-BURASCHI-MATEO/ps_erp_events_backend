package com.tup.ps.erpevents.dtos.event.account.concept;

import com.tup.ps.erpevents.enums.AccountingConcept;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptPostDTO {

    @NotNull
    @Schema(description = "ID de la cuenta asociada")
    private Long idAccount;

    @NotNull
    @Schema(description = "Fecha contable")
    private LocalDateTime accountingDate;

    @NotNull
    @Schema(description = "Concepto contable")
    private AccountingConcept concept;

    @Schema(description = "Comentarios")
    private String comments;

    @NotNull
    @Schema(description = "Monto")
    private BigDecimal amount;

    @Schema(description = "ID de archivo asociado (opcional)")
    private Long idFile;

    @Schema(description = "Tipo de contenido del archivo", example = "application/pdf")
    private String fileContentType;
}
