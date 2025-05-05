package com.tup.ps.erpevents.dtos.guest;

import com.tup.ps.erpevents.enums.GuestType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

    @Schema(description = "ID del invitado", example = "1")
    private Long idGuest;

    @Schema(description = "Nombre del invitado", example = "Carla")
    private String firstName;

    @Schema(description = "Apellido del invitado", example = "González")
    private String lastName;

    @Schema(description = "Tipo de invitado", example = "VIP")
    private GuestType type;

    @Schema(description = "Correo electrónico", example = "carlos.lopez@email.com")
    private String email;

    @Schema(description = "Nota sobre el invitado", example = "Es el presentador del evento")
    private String note;
}

