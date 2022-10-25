package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "review")
public class Review extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ride_id")
	Ride ride;

	@Column(name = "driver_rating")
	Double driverRating;

	@Column(name = "vehicle_rating")
	Double vehicleRating;

	@Column(name = "driver_comment")
	String driverComment;

	@Column(name = "vehicle_comment")
	String vehicleComment;

}
