package com.tup.ps.erpevents.dtos.event.account;

import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @Schema(description = "ID de la cuenta")
    private Long idAccount;

    @Schema(description = "Balance actual de la cuenta")
    private BigDecimal balance;

    @Schema(description = "Fecha de creación")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updateDate;

    @Schema(description = "Borrado lógico")
    private Boolean softDelete;

    @Schema(description = "Movimientos")
    private List<ConceptDTO> concepts;


}
