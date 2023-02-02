package com.kts.taxify.dto.request.ride;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RideReviewRequest {

    UUID rideId;

    Double driverRating;

    Double vehicleRating;

    String comment;
}
