package com.tup.ps.erpevents.entities.intermediates;

import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.SupplierEntity;
import com.tup.ps.erpevents.enums.AmountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@AuditTable(value = "events_suppliers_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events_suppliers")
public class EventsSuppliersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idRelation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEvent")
    private EventEntity event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idSupplier")
    private SupplierEntity supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AmountStatus status;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "payment", nullable = false)
    private String payment;

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
