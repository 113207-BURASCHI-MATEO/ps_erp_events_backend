package com.tup.ps.erpevents.dtos.role;

import com.tup.ps.erpevents.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @Schema(description = "Id del Rol")
    private Long idRole;

    @Schema(description = "Codigo del Rol")
    private Integer roleCode;

    @Schema(description = "Nombre del Rol")
    private RoleName name;

    @Schema(description = "Descripcion del Rol")
    private String description;

}
