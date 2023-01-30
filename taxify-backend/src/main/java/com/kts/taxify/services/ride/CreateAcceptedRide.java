package com.kts.taxify.services.ride;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateAcceptedRide {

    private final CreateAcceptedRideForMultiplePassengers createAcceptedRideForMultiplePassengers;

    private final CreateAcceptedRideForOnePassenger createAcceptedRideForOnePassenger;

    private final GetUserByEmail getUserByEmail;


    public Ride execute(RequestedRideRequest requestedRideRequest, Driver assignedDriver) throws IOException, InterruptedException {
        Passenger sender = (Passenger) getUserByEmail.execute(requestedRideRequest.getPassengers().getSenderEmail());
        if (requestedRideRequest.getPassengers().getRecipientsEmails().size() > 0) {
            return createAcceptedRideForMultiplePassengers.execute(requestedRideRequest, assignedDriver, sender);
        } else {
            return createAcceptedRideForOnePassenger.execute(requestedRideRequest, assignedDriver, sender);
        }
    }
}
