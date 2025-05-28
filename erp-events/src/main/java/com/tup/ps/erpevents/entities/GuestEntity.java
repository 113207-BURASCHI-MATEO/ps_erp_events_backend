package com.tup.ps.erpevents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tup.ps.erpevents.entities.intermediates.EventsGuestsEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsSuppliersEntity;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.enums.GuestType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Audited
@AuditTable(value = "guests_audit")
@EqualsAndHashCode
@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGuest")
    private Long idGuest;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false)
    private DocumentType documentType;

    @Column(name = "documentNumber", nullable = false)
    private String documentNumber;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EventsGuestsEntity> guestEvents = new ArrayList<>();

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

