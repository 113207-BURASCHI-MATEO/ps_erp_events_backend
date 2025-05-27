package com.tup.ps.erpevents.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {
    @NotBlank
    @Schema(description = "Acceso temporal")
    private String token;

    @NotBlank
    @Schema(description = "Nueva contraseña")
    private String newPassword;
}
