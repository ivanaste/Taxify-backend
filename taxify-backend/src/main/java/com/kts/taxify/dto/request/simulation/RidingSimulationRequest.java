package com.kts.taxify.dto.request.simulation;

import com.kts.taxify.dto.request.ride.WaypointRequest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RidingSimulationRequest {
	private String id;
	private List<WaypointRequest> waypoints;
	private final boolean follow = true;
}
