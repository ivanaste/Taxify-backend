package com.kts.taxify.services.passenger;

import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Review;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.auth.GetSelfAsPassenger;
import com.kts.taxify.services.ride.GetRideById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckIfReviewAvailable {
    private final GetSelfAsPassenger getSelfAsPassenger;

    private final GetRideById getRideById;


    public boolean execute(UUID rideId) {
        Ride ride = getRideById.execute(rideId);
        Passenger passenger = getSelfAsPassenger.execute();
        for (Review review : ride.getReviews()) {
            if (review.getPassenger().equals(passenger)) {
                return false;
            }
        }
        return ride.getFinishedAt().plusDays(3).isAfter(LocalDateTime.now());
    }
}
