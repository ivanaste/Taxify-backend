package com.kts.taxify.converter;

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

    public static Route routeRequestToRoute(final RouteRequest routeRequest, VehicleType chosenVehicleType) {
        List<Waypoint> waypoints = new ArrayList<>();
        for (WaypointRequest waypointRequest : routeRequest.getWaypoints()) {
            Location location = Location.builder().latitude(waypointRequest.getLatitude()).longitude(waypointRequest.getLongitude())
                    .build();
            Waypoint waypoint = Waypoint.builder().location(location).stop(waypointRequest.isStop()).build();
            waypoints.add(waypoint);
        }
        Double price = routeRequest.getRouteDistance() + chosenVehicleType.getPrice();
        Route route = Route.builder().price(price).distance(routeRequest.getRouteDistance()).waypoints(waypoints).locationNames(routeRequest.getLocationNames()).build();
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
        for (Waypoint waypoint : ride.getRoute().getWaypoints()) {
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

    public static List<RideHistoryResponse> toRideHistoryResponseList(List<Ride> rides) {
        List<RideHistoryResponse> rideHistoryResponse = new ArrayList<>();
        for (Ride ride : rides) {
            String route = "";
            for (String locationName : ride.getRoute().getLocationNames()) {
                route = route.concat(locationName.split(",")[0]).concat(" - ");
            }
            List<UserResponse> passengers = new ArrayList<>();
            for (User user : ride.getPassengers()) passengers.add(UserConverter.toUserResponse(user));
            rideHistoryResponse.add(new RideHistoryResponse(ride.getId(), route.substring(0, route.length() - 2), ride.getRoute().getLocationNames(), passengers, ride.getRoute().getPrice().toString(), ride.getScheduledAt(), ride.getFinishedAt()));
        }
        return rideHistoryResponse;
    }
}
