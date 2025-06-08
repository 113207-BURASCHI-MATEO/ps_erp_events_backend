package com.tup.ps.erpevents.dtos.employee;

import com.tup.ps.erpevents.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para actualizar un empleado existente")
public class EmployeePutDTO {

    @NotNull(message = "El ID del empleado es obligatorio")
    @Schema(description = "ID del empleado", example = "1", required = true)
    private Long idEmployee;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del empleado", example = "Mateo", required = true)
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Schema(description = "Apellido del empleado", example = "Buraschi", required = true)
    private String lastName;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Schema(description = "Tipo de documento del empleado", example = "DNI", required = true)
    private DocumentType documentType;

    @NotBlank(message = "El número de documento no puede estar vacío")
    @Schema(description = "Número de documento del empleado", example = "32373000", required = true)
    private String documentNumber;

    @Email(message = "El correo electrónico debe ser válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Schema(description = "Correo electrónico del empleado", example = "buraschi.mateo@gmail.com", required = true)
    private String email;

    @NotBlank(message = "El número de CUIT no puede estar vacío")
    @Schema(description = "CUIT del empleado", example = "20323730009", required = true)
    private String cuit;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento del empleado", example = "1986-07-20", required = true)
    private LocalDate birthDate;

    @NotBlank(message = "El alias o CBU no puede estar vacío")
    @Schema(description = "Alias bancario o CBU del empleado", example = "mateo.buraschi.mp", required = true)
    private String aliasCbu;

    @NotBlank
    @Schema(description = "Telefono", example = "3512645248")
    private String phoneNumber;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @Schema(description = "Fecha de contratación del empleado", example = "2024-04-01", required = true)
    private LocalDate hireDate;

    @NotBlank(message = "El puesto no puede estar vacío")
    @Schema(description = "Puesto del empleado", example = "Desarrollador Backend", required = true)
    private String position;

    @NotBlank
    @Schema(description = "Dirección del proveedor", example = "Av. Siempre Viva 123")
    private String address;
}

