package com.tup.ps.erpevents.entities;

import com.tup.ps.erpevents.enums.StatusSend;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@AuditTable(value = "notifications_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;


    @Column(nullable = false)
    private String recipient;

    @Column(nullable = true)
    private Long idContact;

    @Column(nullable = false)
    private String subject;

    @Column(name = "id_template")
    private Long idTemplate;

    @Column(name = "template_name", nullable = true)
    private String templateName;

    @Column(name = "date_send", nullable = false)
    private LocalDateTime dateSend;

    @Column(name = "status_send", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusSend statusSend;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;
}
