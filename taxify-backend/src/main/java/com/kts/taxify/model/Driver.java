package com.kts.taxify.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Value;

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
@Table(name = "driver")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Driver extends User {
	@OneToOne(cascade = CascadeType.ALL)
	Vehicle vehicle;

	@OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
	Set<DriverTimetable> timetables;

	@JsonManagedReference
	@OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
	Set<Ride> rides;

    @Column(name = "active")
    private boolean active;

	@Column(name = "reserved")
	private boolean reserved;
}
