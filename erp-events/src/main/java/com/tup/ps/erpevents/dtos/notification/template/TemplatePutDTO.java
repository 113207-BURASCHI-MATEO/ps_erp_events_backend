package com.tup.ps.erpevents.dtos.notification.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplatePutDTO {

    private Long id;

    private String name;

    private String base64body;
}
