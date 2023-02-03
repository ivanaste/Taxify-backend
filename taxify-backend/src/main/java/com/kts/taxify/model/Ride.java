package com.kts.taxify.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ride")
public class Ride extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    @JsonBackReference
    Driver driver;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ride_passenger", joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    Set<Passenger> passengers;

    @Column(name = "sender", nullable = false)
    String sender;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    RideStatus status;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    Set<Review> reviews;

    @Column(name = "scheduled_at")
    LocalDateTime scheduledAt;

    @Column(name = "finished_at")
    LocalDateTime finishedAt;

    @Column(name = "rejection_reason")
    String rejectionReason;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    Route route;

    @OneToMany(mappedBy = "ride")
    Set<Notification> notifications;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    Set<Complaint> complaints;

    @OneToMany(mappedBy = "ride")
    List<Charge> passengersCharges = new ArrayList<>();

}
