package com.kts.taxify.repository;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    Ride getRideByDriverAndStatus(Driver driver, RideStatus status);

    Ride getRideByPassengersContainingAndStatusIn(Passenger passenger, List<RideStatus> statuses);

    Ride findFirstByDriverAndStatusIn(Driver driver, List<RideStatus> statuses);

    Ride getRideBySenderAndStatus(String senderEmail, RideStatus status);

    Ride getRideBySenderAndStatusIn(String senderEmail, List<RideStatus> statuses);

    List<Ride> getRidesByStatus(RideStatus rideStatus);

    List<Ride> getAllByPassengersContainingAndStatus(Passenger passenger, RideStatus status);

    List<Ride> getAllByDriverAndStatus(Driver passenger, RideStatus status);

}
