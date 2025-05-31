package com.tup.ps.erpevents.dtos.guest;

import com.tup.ps.erpevents.dtos.event.relations.EventsGuestsDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.enums.GuestType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Schema(description = "Telefono", example = "3512645248")
    private String phoneNumber;

    @Schema(description = "Nota sobre el invitado", example = "Es el presentador del evento")
    private String note;

    @Schema(description = "Tipo de documento del invitado", example = "DNI")
    private DocumentType documentType;

    @Schema(description = "Número de documento", example = "40123456")
    private String documentNumber;

    @Schema(description = "Fecha de nacimiento", example = "1980-05-12")
    private LocalDate birthDate;

    @Schema(description = "Relacion con el evento")
    private EventsGuestsDTO acreditation;

    @Schema(description = "Fecha de creación del registro", example = "2024-04-30T10:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última actualización del registro", example = "2024-04-30T12:00:00")
    private LocalDateTime updateDate;

    @Schema(description = "Indica si el empleado fue eliminado lógicamente", example = "false")
    private Boolean softDelete;
}

