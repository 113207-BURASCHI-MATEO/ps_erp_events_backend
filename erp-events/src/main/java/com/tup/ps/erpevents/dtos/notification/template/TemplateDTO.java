package com.tup.ps.erpevents.dtos.notification.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {

    private Long idTemplate;

    private String name;

    private String body;

    private Boolean active;
}
