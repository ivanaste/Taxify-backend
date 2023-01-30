package com.kts.taxify.controller;

import com.kts.taxify.converter.RideConverter;
import com.kts.taxify.dto.response.RideRouteResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.ride.GetAssignedRide;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final GetAssignedRide getAssignedRide;

    @GetMapping(value = "/assignedRideRoute")
    @HasAnyPermission({ Permission.GET_ASSIGNED_RIDE})
    public RideRouteResponse getAssignedRideRoute() {
        return RideConverter.toRideRouteResponse(getAssignedRide.execute());
    }

}
