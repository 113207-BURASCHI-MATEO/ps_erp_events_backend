package com.tup.ps.erpevents.entities;

import com.tup.ps.erpevents.enums.SupplierType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Audited
@AuditTable(value = "suppliers_audit")
@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idSupplier", nullable = false)
    private Long idSupplier;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cuit;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "bankAliasCbu", nullable = false)
    private String aliasCbu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplierType supplierType;

    @Column
    private String address;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToMany(mappedBy = "suppliers")
    private List<EventEntity> events = new ArrayList<>();

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

