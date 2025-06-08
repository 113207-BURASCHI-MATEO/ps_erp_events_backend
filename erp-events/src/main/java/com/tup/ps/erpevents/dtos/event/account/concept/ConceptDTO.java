package com.tup.ps.erpevents.dtos.event.account.concept;

import com.tup.ps.erpevents.enums.AccountingConcept;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptDTO {

    @Schema(description = "ID del concepto")
    private Long idConcept;

    @Schema(description = "ID de la cuenta asociada")
    private Long idAccount;

    @Schema(description = "Fecha contable")
    private LocalDateTime accountingDate;

    @Schema(description = "Concepto contable")
    private AccountingConcept concept;

    @Schema(description = "Comentarios")
    private String comments;

    @Schema(description = "Monto")
    private BigDecimal amount;

    @Schema(description = "ID del archivo asociado (si existe)")
    private Long idFile;

    @Schema(description = "Tipo de contenido del archivo", example = "application/pdf")
    private String fileContentType;

    @Schema(description = "Fecha de creación")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updateDate;

    @Schema(description = "Borrado lógico")
    private Boolean softDelete;
}
