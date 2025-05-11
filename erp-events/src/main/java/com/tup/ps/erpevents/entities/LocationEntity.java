package com.tup.ps.erpevents.entities;

import com.tup.ps.erpevents.enums.Country;
import com.tup.ps.erpevents.enums.Province;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Audited
@AuditTable(value = "locations_audit")
@EqualsAndHashCode
@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLocation")
    private Long idLocation;

    @Column(name = "fantasyName", nullable = false)
    private String fantasyName;

    @Column(name = "streetAddress")
    private String streetAddress;

    @Column(name = "number")
    private Integer number;

    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "province")
    private Province province;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    @Column(name = "postalCode")
    private Integer postalCode;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    /*@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<EventEntity> events = new ArrayList<>();*/


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

