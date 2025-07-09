package com.tup.ps.erpevents.dtos.employee;

import com.tup.ps.erpevents.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para creación y consulta de empleados")
public class EmployeePostDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del empleado", example = "Juan")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Schema(description = "Apellido del empleado", example = "Pérez")
    private String lastName;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Schema(description = "Tipo de documento del empleado", example = "DNI")
    private DocumentType documentType;

    @NotBlank(message = "El número de documento no puede estar vacío")
    @Schema(description = "Número de documento", example = "40123456")
    private String documentNumber;

    @Email(message = "El correo electrónico debe ser válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Schema(description = "Email del empleado", example = "juan.perez@empresa.com")
    private String email;

    @NotBlank
    @Schema(description = "Telefono", example = "3512645248")
    private String phoneNumber;

    @NotBlank(message = "El número de CUIT no puede estar vacío")
    @Schema(description = "CUIT del empleado", example = "20-40123456-7")
    private String cuit;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento", example = "1980-05-12")
    private LocalDate birthDate;

    @NotBlank(message = "El alias o CBU no puede estar vacío")
    @Schema(description = "Alias bancario o CBU", example = "juan.perez.alky")
    private String aliasCbu;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Schema(description = "Contraseña del usuario asociado", example = "ContrasenaSegura123!")
    private String password;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @Schema(description = "Fecha de contratación", example = "2024-01-01")
    private LocalDate hireDate;

    @NotBlank(message = "El cargo no puede estar vacío")
    @Schema(description = "Puesto del empleado", example = "Administrativo")
    private String position;

    @NotBlank
    @Schema(description = "Dirección del proveedor", example = "Av. Siempre Viva 123")
    private String address;
}
