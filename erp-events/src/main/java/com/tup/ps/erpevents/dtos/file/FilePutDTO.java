package com.tup.ps.erpevents.dtos.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePutDTO {

    @NotNull
    @Schema(description = "ID del archivo", example = "1")
    private Long idFile;

    @Schema(description = "Tipo de archivo", example = "RECEIPT")
    private String fileType;

    @Schema(description = "Nombre del archivo", example = "recibo_marzo.pdf")
    private String fileName;

    @Schema(description = "Tipo de contenido del archivo", example = "application/pdf")
    private String fileContentType;

    @Schema(description = "URL del archivo", example = "https://minio.local/files/recibo_marzo.pdf")
    private String fileUrl;

    @Schema(description = "Nota de revisión del archivo", example = "Pendiente de aprobación")
    private String reviewNote;

    @Schema(description = "Estado de borrado lógico", example = "false")
    private Boolean softDelete;

    @Schema(description = "ID del proveedor relacionado")
    private Long supplierId;

    @Schema(description = "ID del cliente relacionado")
    private Long clientId;

    @Schema(description = "ID del empleado relacionado")
    private Long employeeId;

    @Schema(description = "ID del pago relacionado")
    private Long paymentId;
}
