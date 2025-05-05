package com.tup.ps.erpevents.dtos.client;

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
public class ClientPutDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente", example = "1", required = true)
    private Long idClient;

    @NotBlank
    @Schema(description = "Nombre del cliente", example = "Ana")
    private String firstName;

    @NotBlank
    @Schema(description = "Apellido del cliente", example = "Gómez")
    private String lastName;

    @NotBlank
    @Email
    @Schema(description = "Correo electrónico del cliente", example = "ana.gomez@email.com")
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    @Schema(description = "Número de teléfono", example = "3514567890")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "Alias bancario o CBU", example = "ana.gomez.mp")
    private String aliasCbu;
}

