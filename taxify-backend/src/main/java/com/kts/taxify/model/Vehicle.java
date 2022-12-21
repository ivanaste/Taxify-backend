package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {
    @Embedded
    Location location;

    @Column(name = "occupied")
    @Builder.Default
    Boolean occupied = false;

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

    public Boolean isInArea(final Double minLongitude,
                            final Double maxLongitude,
                            final Double minLatitude,
                            final Double maxLatitude) {
        final Double vehicleLongitude = location.getLongitude();
        final Double vehicleLatitude = location.getLatitude();
        return (minLongitude < vehicleLongitude && vehicleLongitude < maxLongitude) &&
                (minLatitude < vehicleLatitude && vehicleLatitude < maxLatitude);
    }
}
