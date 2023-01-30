package com.kts.taxify.dto.request.ride;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.model.Location;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestedRideRequest {

	Location clientLocation;

	RouteRequest routeRequest;

	List<String> vehicleTypes;

	boolean petFriendly;

	boolean babyFriendly;

	LinkedPassengersToTheRideRequest passengers;
}
