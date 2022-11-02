package com.kts.taxify.model;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route extends BaseEntity {

	@ManyToMany
	@JoinTable(name = "route_waypoints", joinColumns = @JoinColumn(name = "route_id"),
		inverseJoinColumns = @JoinColumn(name = "waypoint_id"))
	List<Waypoint> waypoints;

	@ManyToMany(mappedBy = "favoriteRoutes")
	Set<Passenger> subscribedPassengers;

	@Column(name = "price", nullable = false)
	Double price;

	@Column(name = "duration", nullable = false)
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
		return waypoints.stream().filter(Waypoint::getIsStop).sorted(Comparator.comparingInt(Waypoint::getOrdinalNumber))
			.collect(Collectors.toList());
	}
	
}
