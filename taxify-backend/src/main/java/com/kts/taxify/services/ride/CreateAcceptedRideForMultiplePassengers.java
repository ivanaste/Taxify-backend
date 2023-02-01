package com.kts.taxify.services.ride;

import com.kts.taxify.converter.RideConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.model.*;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CreateAcceptedRideForMultiplePassengers {

    private final SaveRide saveRide;

    private final RideRepository rideRepository;

    @Transactional(readOnly = false)
    public Ride execute(RequestedRideRequest requestedRideRequest, Driver driver, Passenger sender) {
        Ride ride = rideRepository.getRideBySenderAndStatus(sender.getEmail(), RideStatus.PENDING);
        Route route = RideConverter.routeResponseToRoute(requestedRideRequest.getRouteRequest());
        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);
        ride.setRoute(route);
        return saveRide.execute(ride);
    }
}
