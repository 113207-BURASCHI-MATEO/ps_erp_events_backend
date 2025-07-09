package com.tup.ps.erpevents.entities;

import com.tup.ps.erpevents.enums.AccountingConcept;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Audited
@AuditTable(value = "concepts_audit")
@EqualsAndHashCode
@Entity
@Table(name = "concepts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConceptEntity {

    private static final int PRECISION = 19;
    private static final int SCALE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConcept")
    private Long idConcept;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idAccount", nullable = false)
    @ToString.Exclude
    private AccountEntity account;

    @Column(name = "accountingDate")
    private LocalDateTime accountingDate;

    @Column(name = "concept")
    @Enumerated(EnumType.STRING)
    private AccountingConcept concept;

    @Column(name = "comments")
    private String comments;

    @Column(name = "amount", precision = PRECISION, scale = SCALE)
    private BigDecimal amount;

    @Column(name = "idFile")
    private Long idFile;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

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
