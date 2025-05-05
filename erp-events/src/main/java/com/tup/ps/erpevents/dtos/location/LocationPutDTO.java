package com.tup.ps.erpevents.dtos.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationPutDTO {

    @NotNull(message = "El ID de la ubicación es obligatorio")
    @Schema(description = "ID de la ubicación", example = "1")
    private Long idLocation;

    @NotBlank(message = "La latitud no puede estar vacía")
    @Schema(description = "Latitud de la ubicación", example = "-31.4167")
    private String latitude;

    @NotBlank(message = "La longitud no puede estar vacía")
    @Schema(description = "Longitud de la ubicación", example = "-64.1833")
    private String longitude;
}

