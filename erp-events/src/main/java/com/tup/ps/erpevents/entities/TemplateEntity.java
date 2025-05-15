package com.tup.ps.erpevents.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@AuditTable(value = "templates_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTemplate;

    @Column
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private Boolean active;
}
