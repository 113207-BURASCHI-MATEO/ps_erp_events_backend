package com.tup.ps.erpevents.dtos.location;

import com.tup.ps.erpevents.enums.Country;
import com.tup.ps.erpevents.enums.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationPostDTO {

    @NotBlank(message = "El nombre de fantasía es obligatorio")
    @Schema(description = "Nombre de fantasía de la ubicación", example = "Salón El Castillo")
    private String fantasyName;

    @Schema(description = "Dirección", example = "Av. Siempre Viva")
    private String streetAddress;

    @Schema(description = "Número", example = "742")
    private Integer number;

    @Schema(description = "Ciudad", example = "Córdoba")
    private String city;

    @NotNull(message = "La provincia es obligatoria")
    @Schema(description = "Provincia", example = "CORDOBA")
    private Province province;

    @NotNull(message = "El país es obligatorio")
    @Schema(description = "País", example = "ARGENTINA")
    private Country country;

    @NotNull(message = "El codigo postal es obligatorio")
    @Schema(description = "Código postal", example = "5000")
    private Integer postalCode;

    @Schema(description = "Latitud", example = "-31.4167")
    private String latitude;

    @Schema(description = "Longitud", example = "-64.1833")
    private String longitude;
}

