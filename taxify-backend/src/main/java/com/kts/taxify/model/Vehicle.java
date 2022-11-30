package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

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

}
