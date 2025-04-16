package com.tup.ps.erpevents.dtos.users;

import com.tup.ps.erpevents.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "DTO para un usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Schema(description = "Id del usuario")
    private Long idUser;

    @Schema(description = "Nombre del usuario")
    private String firstName;

    @Schema(description = "Apellido del usuario")
    private String lastName;

    @Schema(description = "Fecha de nacimiento del usuario")
    private LocalDate birthDate;

    @Schema(description = "Tipo de documento del usuario")
    private DocumentType documentType;

    @Schema(description = "Número de documento del usuario")
    private String documentNumber;

    @Schema(description = "Correo electrónico del usuario")
    private String email;
}
