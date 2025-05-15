package com.tup.ps.erpevents.dtos.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyValueCustomPair {

    @Schema(description = "Clave", example = "Evento")
    private String key;

    @Schema(description = "Valor", example = "XYZ")
    private String value;
}