package com.kts.taxify.services.ride;

import com.kts.taxify.model.*;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRideHistory {
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final RideRepository rideRepository;

    public List<Ride> execute() {
        User user = getUserByEmail.execute(getSelf.execute().getEmail());
        List<Ride> rideHistory;
        if (user instanceof Driver) {
            rideHistory = rideRepository.getAllByDriverAndStatus((Driver) user, RideStatus.FINISHED);
        }
        else {
            rideHistory = rideRepository.getAllByPassengersContainingAndStatus((Passenger) user, RideStatus.FINISHED);
        }
        Collections.reverse(rideHistory);
        return rideHistory;
    }
}
