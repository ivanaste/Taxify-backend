package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {

	@OneToOne(mappedBy = "vehicle")
	Driver driver;

	@Embedded
	Location location;

	@Column(name = "occupied", nullable = false)
	Boolean occupied;

	@Column(name = "brand", nullable = false)
	String brand;

	@Column(name = "model", nullable = false)
	String model;

	@Column(name = "horse_power", nullable = false)
	Integer horsePower;

	@Column(name = "type", nullable = false)
	VehicleType type;

	@Column(name = "baby_friendly", nullable = false)
	Boolean babyFriendly;

	@Column(name = "pet_friendly", nullable = false)
	Boolean petFriendly;

}
