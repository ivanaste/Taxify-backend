package com.kts.taxify.services.ride;

import com.kts.taxify.converter.RideConverter;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLastFinishedRideOfPassenger {

    private final RideRepository rideRepository;

    private final GetSelf getSelf;

    private final GetUserByEmail getUserByEmail;

    @Transactional
    public RideResponse execute() {
        Passenger passenger = (Passenger) getUserByEmail.execute(getSelf.execute().getEmail());
        List<Ride> finishedRides = rideRepository.getRidesByStatus(RideStatus.FINISHED);
        Collections.reverse(finishedRides);
        for (Ride ride : finishedRides) {
            if (ride.getPassengers().contains(passenger)) {
                return RideConverter.toRideResponse(ride);
            }
        }
        return null;
    }
}
