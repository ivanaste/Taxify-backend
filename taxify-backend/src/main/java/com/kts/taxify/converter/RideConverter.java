package com.kts.taxify.converter;

import com.google.gson.JsonObject;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.request.ride.RouteRequest;
import com.kts.taxify.dto.request.ride.WaypointRequest;
import com.kts.taxify.dto.response.*;
import com.kts.taxify.model.*;
import org.modelmapper.ModelMapper;

import java.util.*;

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

    public static RideRouteResponse toRideRouteResponse(final Ride ride) {
        if (Objects.isNull(ride)) return null;
        RideRouteResponse rideRouteResponse = new RideRouteResponse();
        rideRouteResponse.setDriver(DriverConverter.toDriverResponse(ride.getDriver()));
        rideRouteResponse.setRideStatus(ride.getStatus());
        int counter = 1;
        List<WaypointResponse> routePart = new ArrayList<>();
        Map<String, List<WaypointResponse>> route = new HashMap<>();
        for(Waypoint waypoint: ride.getRoute().getWaypoints()) {
            if (waypoint.isStop()) {
                routePart.add(new WaypointResponse(waypoint.getLocation().getLongitude(), waypoint.getLocation().getLatitude(), waypoint.isStop()));
                String key = "location".concat(String.valueOf(counter));
                route.put(key, routePart);
                routePart = new ArrayList<>();
                counter++;
                continue;
            }
            routePart.add(new WaypointResponse(waypoint.getLocation().getLongitude(), waypoint.getLocation().getLatitude(), waypoint.isStop()));
        }
        rideRouteResponse.setRoute(route);
        return rideRouteResponse;
    }
}
