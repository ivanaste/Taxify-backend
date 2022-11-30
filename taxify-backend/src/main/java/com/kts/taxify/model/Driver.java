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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "driver")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Driver extends User {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicle_id", referencedColumnName = "id")
	Vehicle vehicle;

	@OneToMany(mappedBy = "driver")
	Set<DriverTimetable> timetables;

	@OneToMany(mappedBy = "driver")
	Set<Ride> ride;

	@Column(name = "blocked")
	@Value("false")
	private boolean blocked;

	@Column(name = "active")
	private boolean active;
}
