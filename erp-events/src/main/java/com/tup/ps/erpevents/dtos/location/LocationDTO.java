package com.tup.ps.erpevents.dtos.location;

import com.tup.ps.erpevents.enums.Country;
import com.tup.ps.erpevents.enums.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    @Schema(description = "ID de la ubicación", example = "1")
    private Long idLocation;

    @Schema(description = "Nombre de fantasía de la ubicación", example = "Salón El Castillo")
    private String fantasyName;

    @Schema(description = "Dirección", example = "Av. Siempre Viva")
    private String streetAddress;

    @Schema(description = "Número", example = "742")
    private Integer number;

    @Schema(description = "Ciudad", example = "Córdoba")
    private String city;

    @Schema(description = "Provincia", example = "CORDOBA")
    private Province province;

    @Schema(description = "País", example = "ARGENTINA")
    private Country country;

    @Schema(description = "Código postal", example = "5000")
    private Integer postalCode;

    @Schema(description = "Latitud", example = "-31.4167")
    private Double latitude;

    @Schema(description = "Longitud", example = "-64.1833")
    private Double longitude;

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;
}

