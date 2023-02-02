package com.kts.taxify.services.ride;

import com.kts.taxify.converter.RideConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.model.*;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAcceptedRideForOnePassenger {
	private final SaveRide saveRide;

	public Ride execute(RequestedRideRequest requestedRideRequest, Driver driver, Passenger sender) {
		Route route = RideConverter.routeRequestToRoute(requestedRideRequest.getRouteRequest());
		Ride ride = Ride.builder().driver(driver).status(RideStatus.ACCEPTED).route(route).passengers(new HashSet<>(Collections.singletonList(sender))).sender(sender.getEmail()).build();
		return saveRide.execute(ride);
	}
}
