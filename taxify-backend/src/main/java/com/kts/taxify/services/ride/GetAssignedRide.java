package com.kts.taxify.services.ride;

import com.kts.taxify.model.*;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GetAssignedRide {
    private final GetUserByEmail getUserByEmail;
    private final GetSelf getSelf;
    private final RideRepository rideRepository;
    private final NotifyDriver notifyDriver;

    public Ride execute() {
        User user = getUserByEmail.execute(getSelf.execute().getEmail());
        Ride ride;
        if (user instanceof Driver) {
            ride = rideRepository.findFirstByDriverAndStatusIn((Driver) user, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION));
            if (ride != null && ride.getStatus().equals(RideStatus.ON_DESTINATION))
                notifyDriver.execute(user.getEmail(), NotificationType.ON_DESTINATION_DRIVER);
        } else {
            ride = rideRepository.getRideByPassengersContainingAndStatusIn((Passenger) user, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION));
        }
        return ride;
    }
}
