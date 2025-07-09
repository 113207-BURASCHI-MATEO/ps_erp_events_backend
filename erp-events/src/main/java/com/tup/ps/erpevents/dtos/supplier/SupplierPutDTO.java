package com.tup.ps.erpevents.dtos.supplier;

import com.tup.ps.erpevents.enums.SupplierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPutDTO {

    @NotNull(message = "El ID del proveedor es obligatorio")
    @Schema(description = "ID del proveedor", example = "1")
    private Long idSupplier;

    @NotBlank
    @Schema(description = "Nombre del proveedor", example = "Eventos XYZ")
    private String name;

    @NotBlank
    @Schema(description = "CUIT del proveedor", example = "20304567891")
    private String cuit;

    @Email
    @NotBlank
    @Schema(description = "Correo electrónico del proveedor", example = "proveedor@xyz.com")
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    @Schema(description = "Número de teléfono", example = "3511234567")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "Alias bancario o CBU", example = "eventos.xyz.cbu")
    private String aliasCbu;

    @NotNull
    @Schema(description = "Tipo de proveedor", example = "CATERING")
    private SupplierType supplierType;

    @NotBlank
    @Schema(description = "Dirección del proveedor", example = "Av. Siempre Viva 123")
    private String address;
}
