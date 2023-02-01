package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "passenger")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Passenger extends User {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    PassengerStatus status;

    @ManyToMany(mappedBy = "passengers")
    Set<Ride> rides;

    @ManyToMany
    @JoinTable(name = "passenger_favorite_routes", joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    Set<Route> favoriteRoutes;

    @Column(name = "in_ride", nullable = false)
    boolean inRide;

    @Column(name = "customer_id")
    String customerId;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    Set<Notification> receivedNotifications;
}
