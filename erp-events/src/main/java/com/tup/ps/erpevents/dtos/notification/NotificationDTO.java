package com.tup.ps.erpevents.dtos.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private String recipient;

    private String subject;

    private Long idTemplate;

    private String templateName;

    private Long idContact;

    private String body;

    private List<KeyValueCustomPair> variables = new ArrayList<>();
}
