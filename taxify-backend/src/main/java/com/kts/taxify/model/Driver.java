package com.kts.taxify.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "driver")
public class Driver extends User {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicle_id", referencedColumnName = "id")
	Vehicle vehicle;

	@Value("false")
	@Column(name = "available", nullable = false)
	Boolean available;

	@OneToMany(mappedBy = "driver")
	Set<DriverTimetable> timetables;

	@OneToMany(mappedBy = "driver")
	Set<Ride> ride;

	@Column(name = "rejection_reason")
	String rejectionReason;
}
