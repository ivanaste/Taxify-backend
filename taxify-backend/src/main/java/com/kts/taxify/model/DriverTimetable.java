package com.kts.taxify.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "driver_timetable")
public class DriverTimetable extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "driver_id")
	Driver driver;

	@Column(name = "start_time", nullable = false)
	LocalDateTime startTime;

	@Column(name = "end_time")
	LocalDateTime endTime;
}
