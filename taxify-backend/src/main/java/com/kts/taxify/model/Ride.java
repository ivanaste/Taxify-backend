package com.kts.taxify.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
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
	RideStatus status;

	@OneToMany(mappedBy = "ride")
	Set<Review> reviews;

	@Column(name = "scheduled_at")
	LocalDateTime scheduledAt;

	//izabrana ruta
	//Set<Route>

}
