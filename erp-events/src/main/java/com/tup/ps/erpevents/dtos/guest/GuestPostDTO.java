package com.tup.ps.erpevents.dtos.guest;

import com.tup.ps.erpevents.enums.GuestType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestPostDTO {

    @NotBlank
    @Schema(description = "Nombre del invitado", example = "Carla")
    private String firstName;

    @NotBlank
    @Schema(description = "Apellido del invitado", example = "González")
    private String lastName;

    @NotNull
    @Schema(description = "Tipo de invitado", example = "VIP")
    private GuestType type;

    @Email
    @NotBlank
    @Schema(description = "Correo electrónico", example = "carlos.lopez@email.com")
    private String email;

    @Schema(description = "Nota sobre el invitado", example = "Es el presentador del evento")
    private String note;
}
