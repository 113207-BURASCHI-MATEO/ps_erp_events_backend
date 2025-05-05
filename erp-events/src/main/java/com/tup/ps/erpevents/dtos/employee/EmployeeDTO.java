package com.tup.ps.erpevents.dtos.employee;

import com.tup.ps.erpevents.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la consulta de empleados")
public class EmployeeDTO {

    @Schema(description = "ID del empleado", example = "1")
    private Long idEmployee;

    @Schema(description = "Nombre del empleado", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido del empleado", example = "Pérez")
    private String lastName;

    @Schema(description = "Tipo de documento del empleado", example = "DNI")
    private DocumentType documentType;

    @Schema(description = "Número de documento", example = "40123456")
    private String documentNumber;

    @Schema(description = "Email del empleado", example = "juan.perez@empresa.com")
    private String email;

    @Schema(description = "CUIT del empleado", example = "20-40123456-7")
    private String cuit;

    @Schema(description = "Fecha de nacimiento", example = "1980-05-12")
    private LocalDate birthDate;

    @Schema(description = "Alias bancario o CBU", example = "juan.perez.alky")
    private String aliasOrCbu;

    @Schema(description = "Fecha de contratación", example = "2024-01-01")
    private LocalDate hireDate;

    @Schema(description = "Puesto del empleado", example = "Administrativo")
    private String position;

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;
}
