package com.kts.taxify.model;

public enum VehicleType {
    SEDAN("Sedan", 120.00),
    STATION_WAGON("Station Wagon", 130.00),
    HATCHBACK("Hatchback", 100.00),
    MINIVAN("Minivan", 140.00),
    SUV("SUV", 150.00);

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
