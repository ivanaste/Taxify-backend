package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ride")
public class Ride extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    Driver driver;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ride_passenger", joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    Set<Passenger> passengers;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    RideStatus status;

    @OneToMany(mappedBy = "ride")
    Set<Review> reviews;

    @Column(name = "scheduled_at")
    LocalDateTime scheduledAt;

    @Column(name = "rejection_reason")
    String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "route_id")
    Route route;

}
