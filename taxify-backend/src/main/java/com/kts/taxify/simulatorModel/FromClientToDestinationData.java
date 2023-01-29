package com.kts.taxify.simulatorModel;

import com.kts.taxify.dto.request.ride.WaypointRequest;
import com.kts.taxify.model.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@lombok.Data
@AllArgsConstructor
public class FromClientToDestinationData {
    private String id;
    private List<Waypoint> waypoints;
    private final boolean follow = true;
}
