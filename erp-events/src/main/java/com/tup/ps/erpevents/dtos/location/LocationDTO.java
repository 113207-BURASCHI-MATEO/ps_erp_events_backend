package com.tup.ps.erpevents.dtos.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    @Schema(description = "ID de la ubicación", example = "1")
    private Long idLocation;

    @Schema(description = "Latitud del evento", example = "-31.4167")
    private Double latitude;

    @Schema(description = "Longitud del evento", example = "-64.1833")
    private Double longitude;
}

