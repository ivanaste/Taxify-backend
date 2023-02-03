package com.kts.taxify.dto.response;

import com.kts.taxify.model.RideStatus;
import com.kts.taxify.model.Waypoint;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RideRouteResponse {
    private Map<String, List<WaypointResponse>> route;
    private DriverResponse driver;
    private RideStatus rideStatus;
    private Double distance;
}
