package com.kts.taxify.model;

public enum VehicleType {
    SEDAN("Sedan", 1.00),
    STATION_WAGON("Station Wagon", 1.10),
    HATCHBACK("Hatchback", 0.9),
    MINIVAN("Minivan", 1.10),
    SUV("SUV", 1.5);

    private final String name;

    private final Double price;

    VehicleType(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
