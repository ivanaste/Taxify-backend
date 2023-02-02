package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "review")
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ride_id")
    Ride ride;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    Passenger passenger;

    @Column(name = "driver_rating")
    Double driverRating;

    @Column(name = "vehicle_rating")
    Double vehicleRating;

    @Column(name = "comment")
    String comment;
}
