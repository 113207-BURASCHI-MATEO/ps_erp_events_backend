package com.tup.ps.erpevents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tup.ps.erpevents.entities.intermediates.EventsSuppliersEntity;
import com.tup.ps.erpevents.enums.SupplierType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Audited
@AuditTable(value = "suppliers_audit")
@EqualsAndHashCode
@Entity
@Table(name = "suppliers")
@Getter
@Setter
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

    @Column(name = "phoneNumber", nullable = false)
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

    /*@ManyToMany(mappedBy = "suppliers")
    private List<EventEntity> events = new ArrayList<>();*/

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EventsSuppliersEntity> supplierEvents = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<FileEntity> files = new ArrayList<>();

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

