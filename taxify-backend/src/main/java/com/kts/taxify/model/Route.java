package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route extends BaseEntity {

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    List<Waypoint> waypoints;

    @ElementCollection
    List<String> locationNames;

    @ManyToMany(mappedBy = "favoriteRoutes")
    Set<Passenger> subscribedPassengers;

    @Column(name = "price")
    Double price;

    @Column(name = "distance")
    Double distance;

    @OneToMany(mappedBy = "route")
    Set<Ride> rides;

    Waypoint getDeparture() {
        return waypoints.stream().filter(waypoint -> waypoint.getOrdinalNumber() == 0).findFirst().orElse(null);
    }

    Waypoint getDestination() {
        return waypoints.stream().filter(waypoint -> waypoint.getOrdinalNumber() == waypoints.size() - 1).findFirst().orElse(null);
    }

    List<Waypoint> getStops() {
        return waypoints.stream().filter(Waypoint::isStop).sorted(Comparator.comparingInt(Waypoint::getOrdinalNumber))
                .collect(Collectors.toList());
    }

}
