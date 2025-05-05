package com.tup.ps.erpevents.entities;

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
@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_employees",
            joinColumns = @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idEmployee"))
    private Set<EmployeeEntity> employees = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_suppliers",
            joinColumns = @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idSupplier"))
    private Set<SupplierEntity> suppliers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_guests",
            joinColumns = @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idGuest"))
    private Set<GuestEntity> guests = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TaskEntity> tasks = new ArrayList<>();

    //@OneToOne(cascade = CascadeType.ALL)
    /*@OneToOne
    @JoinColumn(name = "idLocation", referencedColumnName = "idLocation", nullable = false)
    private LocationEntity location;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClient", nullable = false)
    private ClientEntity client;

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

