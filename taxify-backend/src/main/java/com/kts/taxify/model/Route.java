package com.kts.taxify.model;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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

	@ManyToMany(mappedBy = "favoriteRoutes")
	Set<Passenger> subscribedPassengers;

	@Column(name = "price")
	Double price;

	@Column(name = "duration")
	Double duration;

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
