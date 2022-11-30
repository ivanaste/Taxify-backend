package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
