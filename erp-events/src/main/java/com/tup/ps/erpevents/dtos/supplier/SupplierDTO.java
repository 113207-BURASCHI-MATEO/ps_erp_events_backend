package com.tup.ps.erpevents.dtos.supplier;

import com.tup.ps.erpevents.enums.SupplierType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {

    @Schema(description = "ID del proveedor", example = "1")
    private Long idSupplier;

    @Schema(description = "Nombre del proveedor", example = "Eventos XYZ")
    private String name;

    @Schema(description = "CUIT del proveedor", example = "20304567891")
    private String cuit;

    @Schema(description = "Correo electrónico del proveedor", example = "proveedor@xyz.com")
    private String email;

    @Schema(description = "Número de teléfono (10 dígitos)", example = "3511234567")
    private String phoneNumber;

    @Schema(description = "Alias bancario o CBU", example = "eventos.xyz.cbu")
    private String aliasCbu;

    @Schema(description = "Tipo de proveedor", example = "CATERING")
    private SupplierType supplierType;

    @Schema(description = "Dirección del proveedor", example = "Av. Siempre Viva 123")
    private String address;

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;
}

