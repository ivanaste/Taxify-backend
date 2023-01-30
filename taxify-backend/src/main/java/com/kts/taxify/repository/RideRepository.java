package com.kts.taxify.repository;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;

import java.util.Set;
import java.util.UUID;

import com.kts.taxify.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    Ride getRideByDriverAndStatus(Driver driver, RideStatus status);
    Ride getRideByPassengersContainingAndStatusOrStatusOrStatus(Passenger passenger, RideStatus accepted, RideStatus arrived, RideStatus stated);
    Ride getRideByDriverAndStatusOrStatusOrStatus(Driver driver, RideStatus accepted, RideStatus arrived, RideStatus started);

    Ride getRideBySenderAndStatus(String senderEmail, RideStatus status);
}
