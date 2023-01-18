package com.kts.taxify.dto.request.simulation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RidingSimulationRequest {
    private String id;
    private List<LocationRequest> waypoints;
    private final boolean follow = true;
}
