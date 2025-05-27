package com.tup.ps.erpevents.dtos.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePostDTO {
    @NotBlank(message = "El tipo de archivo es obligatorio")
    @Schema(description = "Tipo de archivo", example = "RECEIPT")
    private String fileType;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Schema(description = "Nombre del archivo", example = "recibo_marzo.pdf")
    private String fileName;

    @NotBlank(message = "El tipo de contenido es obligatorio")
    @Schema(description = "Tipo de contenido del archivo", example = "application/pdf")
    private String fileContentType;

    @Schema(description = "URL del archivo", example = "https://minio.local/files/recibo_marzo.pdf")
    private String fileUrl;

    @Schema(description = "Nota de revisión del archivo", example = "Aprobado")
    private String reviewNote;

    @Schema(description = "ID del proveedor relacionado")
    private Long supplierId;

    @Schema(description = "ID del cliente relacionado")
    private Long clientId;

    @Schema(description = "ID del empleado relacionado")
    private Long employeeId;
}