package com.tup.ps.erpevents.dtos.guest;

import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.enums.GuestType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestPutDTO {

    @NotNull(message = "El ID del invitado es obligatorio")
    @Schema(description = "ID del invitado", example = "1")
    private Long idGuest;

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

    @NotNull
    @Schema(description = "Tipo de documento del invitado", example = "DNI")
    private DocumentType documentType;

    @NotNull
    @Schema(description = "Número de documento", example = "40123456")
    private String documentNumber;

    @NotNull
    @Schema(description = "Fecha de nacimiento", example = "1980-05-12")
    private LocalDate birthDate;

    @NotNull
    @Schema(description = "Id del Evento asociado", example = "1")
    private Long idEvent;
}

