package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {

	@OneToOne
	Driver driver;

	//lokacija vozila

	@Value("false")
	@Column(name = "occupied", nullable = false)
	Boolean occupied;

	@Column(name = "brand", nullable = false)
	String brand;

	@Column(name = "model", nullable = false)
	String model;

	@Column(name = "horsePower", nullable = false)
	Integer horsePower;

	@Column(name = "type", nullable = false)
	VehicleType type;

	@Column(name = "baby_friendly", nullable = false)
	Boolean babyFriendly;

	@Column(name = "pet_friendly", nullable = false)
	Boolean petFriendly;

}
