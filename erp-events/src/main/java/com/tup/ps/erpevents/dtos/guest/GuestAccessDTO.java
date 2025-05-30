package com.tup.ps.erpevents.dtos.guest;

import com.tup.ps.erpevents.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestAccessDTO {

    @NotNull(message = "El ID del invitado es obligatorio")
    @Schema(description = "ID del invitado", example = "1")
    private Long idGuest;

    @NotNull(message = "El ID del evento es obligatorio")
    @Schema(description = "ID del evento", example = "1")
    private Long idEvent;

    @NotNull(message = "El tipo de acceso es obligatorio")
    @Schema(description = "Tipo de acceso", example = "ENTRY/EXIT")
    private AccessType accessType;

    @NotNull(message = "La fecha de acceso o salida es obligatoria")
    @Schema(description = "Fecha del acceso", example = "1980-05-12")
    private LocalDateTime actionDate;
}
