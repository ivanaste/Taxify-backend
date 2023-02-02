package com.kts.taxify.controller;

import com.kts.taxify.converter.RideConverter;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.dto.response.RideHistoryResponse;
import com.kts.taxify.dto.response.RideRouteResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.ride.GetAssignedRide;
import com.kts.taxify.services.ride.GetLastFinishedRideOfPassenger;
import com.kts.taxify.services.ride.GetRideById;
import com.kts.taxify.services.ride.GetRideHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final GetAssignedRide getAssignedRide;

    private final GetLastFinishedRideOfPassenger getLastFinishedRideOfPassenger;

    private final GetRideHistory getRideHistory;
    private final GetRideById getRideById;

    @GetMapping(value = "/assignedRideRoute")
    @HasAnyPermission({ Permission.GET_ASSIGNED_RIDE })
    public RideRouteResponse getAssignedRideRoute() {
        return RideConverter.toRideRouteResponse(getAssignedRide.execute());
    }

    @GetMapping(value = "/lastFinishedRide")
    @HasAnyPermission({ Permission.LAST_FINISHED_RIDE_OF_PASSENGER })
    public RideResponse getLastFinishedPassengerRide() {
        return getLastFinishedRideOfPassenger.execute();
    }

    @GetMapping(value = "/getRouteDetails/{id}")
    public RideRouteResponse getRouteDetails(@PathVariable("id") UUID id) {
        return RideConverter.toRideRouteResponse(getRideById.execute(id));
    }

    @GetMapping(value = "/rideHistory")
    @HasAnyPermission({ Permission.GET_RIDE_HISTORY })
    public List<RideHistoryResponse> getRideHistory() {
        return RideConverter.toRideHistoryResponseList(getRideHistory.execute());
    }

}
