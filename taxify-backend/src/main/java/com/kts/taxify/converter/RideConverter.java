package com.kts.taxify.converter;

import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.request.ride.RouteRequest;
import com.kts.taxify.dto.request.ride.WaypointRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class RideConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static RideResponse toRideResponse(final Ride ride) {
        return modelMapper.map(ride, RideResponse.class);
    }

    public static Route routeResponseToRoute(final RouteRequest routeRequest) {
        List<Waypoint> waypoints = new ArrayList<>();
        for (WaypointRequest waypointRequest : routeRequest.getWaypoints()) {
            Location location = Location.builder().latitude(waypointRequest.getLatitude()).longitude(waypointRequest.getLongitude())
                    .build();
            Waypoint waypoint = Waypoint.builder().location(location).stop(waypointRequest.isStop()).build();
            waypoints.add(waypoint);
        }
        Route route = Route.builder().waypoints(waypoints).build();
        route.getWaypoints().forEach(waypoint -> waypoint.setRoute(route));
        return route;
    }
}
