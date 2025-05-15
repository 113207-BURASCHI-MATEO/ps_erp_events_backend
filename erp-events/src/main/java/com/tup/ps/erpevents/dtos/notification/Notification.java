package com.tup.ps.erpevents.dtos.notification;

import com.tup.ps.erpevents.enums.StatusSend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private Long id;

    private String recipient;

    private Long contactId;

    private NotificationDTO emailNotification;

    private String dateSend;

    private StatusSend statusSend;

    private LocalDateTime createdDate;

    private String body;
}
