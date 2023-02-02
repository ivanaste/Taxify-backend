package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.ride.RideReviewRequest;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Review;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRideReview {

    private final GetRideById getRideById;
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final SaveRide saveRide;

    public void execute(RideReviewRequest reviewRequest) {
        Ride ride = getRideById.execute(reviewRequest.getRideId());
        Review review = Review.builder().ride(ride).passenger((Passenger) getUserByEmail.execute(getSelf.execute().getEmail())).comment(reviewRequest.getComment()).driverRating(reviewRequest.getDriverRating()).vehicleRating(reviewRequest.getVehicleRating()).build();
        ride.getReviews().add(review);
        saveRide.execute(ride);
    }
}
