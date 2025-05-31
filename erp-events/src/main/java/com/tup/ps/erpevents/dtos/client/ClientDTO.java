package com.tup.ps.erpevents.dtos.client;

import com.tup.ps.erpevents.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @Schema(description = "ID del cliente", example = "1")
    private Long idClient;

    @Schema(description = "Nombre del cliente", example = "Ana")
    private String firstName;

    @Schema(description = "Apellido del cliente", example = "Gómez")
    private String lastName;

    @Schema(description = "Correo electrónico del cliente", example = "ana.gomez@email.com")
    private String email;

    @Schema(description = "Número de teléfono", example = "3514567890")
    private String phoneNumber;

    @Schema(description = "Alias bancario o CBU", example = "ana.gomez.mp")
    private String aliasCbu;

    @Schema(description = "Tipo de documento del cliente", example = "DNI")
    private DocumentType documentType;

    @Schema(description = "Número de documento", example = "40123456")
    private String documentNumber;

    /*@Schema(description = "CUIT del cliente", example = "20-40123456-7")
    private String cuit;*/

    @Schema(description = "IDs de eventos asociados al cliente", example = "[1, 2, 3]")
    private List<Long> events;

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;

}

