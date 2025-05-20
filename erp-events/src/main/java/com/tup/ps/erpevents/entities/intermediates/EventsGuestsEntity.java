package com.tup.ps.erpevents.entities.intermediates;

import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.GuestEntity;
import com.tup.ps.erpevents.entities.SupplierEntity;
import com.tup.ps.erpevents.enums.AmountStatus;
import com.tup.ps.erpevents.enums.GuestType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@AuditTable(value = "events_guests_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events_guests")
public class EventsGuestsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idRelation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEvent")
    private EventEntity event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idGuest")
    private GuestEntity guest;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private GuestType type;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

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
