package com.tup.ps.erpevents.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Audited
@AuditTable(value = "time_schedules_audit")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "time_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTimeSchedule")
    private Long idTimeSchedule;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "timeSchedule")
    private EventEntity event;

    @OneToMany(mappedBy = "timeSchedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("scheduledTime ASC")
    private List<ScheduledTaskEntity> scheduledTasks = new ArrayList<>();

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
