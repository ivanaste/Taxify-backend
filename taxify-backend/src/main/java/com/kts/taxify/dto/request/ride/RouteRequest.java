package com.kts.taxify.dto.request.ride;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteRequest {

    private List<WaypointRequest> waypoints;
    private List<String> locationNames;
    private Double routeDistance;


}
