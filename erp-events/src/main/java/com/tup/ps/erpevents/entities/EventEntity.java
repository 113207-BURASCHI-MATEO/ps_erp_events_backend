package com.tup.ps.erpevents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tup.ps.erpevents.entities.intermediates.EventsEmployeesEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsGuestsEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsSuppliersEntity;
import com.tup.ps.erpevents.enums.EventStatus;
import com.tup.ps.erpevents.enums.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Audited
@AuditTable(value = "events_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvent")
    private Long idEvent;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "eventType", nullable = false)
    private EventType eventType;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EventsEmployeesEntity> eventEmployees = new ArrayList<>();

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EventsSuppliersEntity> eventSuppliers = new ArrayList<>();

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EventsGuestsEntity> eventGuests = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<TaskEntity> tasks = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTimeSchedule")
    private TimeScheduleEntity timeSchedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLocation", nullable = false)
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClient", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private ClientEntity client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAccount")
    private AccountEntity account;

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

