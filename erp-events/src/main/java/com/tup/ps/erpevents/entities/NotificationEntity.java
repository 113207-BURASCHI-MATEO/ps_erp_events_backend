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

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "idTemplate")
    private Long idTemplate;

    @Column(name = "templateName", nullable = true)
    private String templateName;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "dateSend", nullable = false)
    private LocalDateTime dateSend;

    @Column(name = "statusSend", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusSend statusSend;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @PrePersist
    public void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }

}
