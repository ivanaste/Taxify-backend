package com.kts.taxify.services.ride;

import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.*;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAssignedRide {
    private final GetUserByEmail getUserByEmail;
    private final GetSelf getSelf;
    private final RideRepository rideRepository;

    public Ride execute() {
        User user = getUserByEmail.execute(getSelf.execute().getEmail());
        if (user instanceof Driver) {
            return rideRepository.getRideByDriverAndStatusOrStatusOrStatus((Driver) user, RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED);
        }
        else {
            return rideRepository.getRideByPassengersContainingAndStatusOrStatusOrStatus((Passenger) user, RideStatus.ACCEPTED,  RideStatus.ARRIVED, RideStatus.STARTED);
        }
    }
}
