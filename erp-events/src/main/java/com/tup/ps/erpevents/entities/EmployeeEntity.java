package com.tup.ps.erpevents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tup.ps.erpevents.entities.intermediates.EventsEmployeesEntity;
import com.tup.ps.erpevents.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Audited
@AuditTable(value = "employees_audit")
@EqualsAndHashCode
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private Long idEmployee;

    @Column(name = "hireDate", nullable = false)
    private LocalDate hireDate;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false)
    private DocumentType documentType;

    @Column(name = "documentNumber", nullable = false)
    private String documentNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "cuit", nullable = false, unique = true)
    private String cuit;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "bankAliasCbu", nullable = false)
    private String aliasCbu;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false)
    private UserEntity user;

    /*@ManyToMany(mappedBy = "employees")
    private List<EventEntity> events = new ArrayList<>();*/

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EventsEmployeesEntity> employeeEvents = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
