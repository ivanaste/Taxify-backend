package com.kts.taxify.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

	@OneToMany(mappedBy = "ride")
	Set<Review> reviews;

	@Column(name = "scheduled_at")
	LocalDateTime scheduledAt;

	@Column(name = "rejection_reason")
	String rejectionReason;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "route_id")
	Route route;

	@OneToMany(mappedBy = "ride")
	Set<Notification> notifications;

}
