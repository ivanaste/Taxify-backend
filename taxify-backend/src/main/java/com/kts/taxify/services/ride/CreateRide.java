package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.request.ride.WaypointRequest;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.model.Route;
import com.kts.taxify.model.Waypoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateRide {
	private final SaveRide saveRide;

	public Ride execute(RequestedRideRequest requestedRideRequest, Driver driver) {
		List<Waypoint> waypoints = new ArrayList<>();
		for (WaypointRequest waypointRequest : requestedRideRequest.getRouteRequest().getWaypoints()) {
			Location location = Location.builder().latitude(waypointRequest.getLatitude()).longitude(waypointRequest.getLongitude())
				.build();
			Waypoint waypoint = Waypoint.builder().location(location).stop(waypointRequest.isStop()).build();
			waypoints.add(waypoint);
		}
		Route route = Route.builder().waypoints(waypoints).build();
		route.getWaypoints().forEach(waypoint -> waypoint.setRoute(route));
		Ride ride = Ride.builder().driver(driver).status(RideStatus.ACCEPTED).route(route).build();
		return saveRide.execute(ride);
	}
}
