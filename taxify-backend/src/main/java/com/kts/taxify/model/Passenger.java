package com.kts.taxify.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

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

	@Column(name = "inRide", nullable = false)
	boolean inRide;

	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	Set<Notification> sentNotifications;

	@OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
	Set<Notification> receivedNotifications;
}
