package com.tup.ps.erpevents.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@AuditTable(value = "scheduled_tasks_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "scheduled_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTask", nullable = false)
    private TaskEntity task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTimeSchedule", nullable = false)
    private TimeScheduleEntity timeSchedule;
}
