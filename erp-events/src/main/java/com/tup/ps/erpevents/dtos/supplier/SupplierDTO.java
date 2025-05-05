package com.tup.ps.erpevents.dtos.supplier;

import com.tup.ps.erpevents.enums.SupplierType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String aliasOrCbu;

    @Schema(description = "Tipo de proveedor", example = "CATERING")
    private SupplierType supplierType;

    @Schema(description = "Dirección del proveedor", example = "Av. Siempre Viva 123")
    private String address;
}

