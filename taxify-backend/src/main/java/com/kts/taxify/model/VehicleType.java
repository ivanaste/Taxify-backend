package com.kts.taxify.model;

public enum VehicleType {
	SEDAN("Sedan"),
	STATION_WAGON("Station Wagon"),
	HATCHBACK("Hatchback"),
	MINIVAN("Minivan"),
	SUV("SUV");

	private final String name;

	VehicleType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
