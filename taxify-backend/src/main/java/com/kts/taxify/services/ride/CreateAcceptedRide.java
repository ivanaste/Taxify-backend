package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateAcceptedRide {

    private final CreateAcceptedRideForMultiplePassengers createAcceptedRideForMultiplePassengers;

    private final CreateAcceptedRideForOnePassenger createAcceptedRideForOnePassenger;

    private final GetUserByEmail getUserByEmail;

    private final AddChargeToRide addChargeToRide;


    public Ride execute(RequestedRideRequest requestedRideRequest, Driver assignedDriver) throws IOException, InterruptedException {
        Passenger sender = (Passenger) getUserByEmail.execute(requestedRideRequest.getPassengers().getSenderEmail());
        Ride ride;
        if (requestedRideRequest.getPassengers().getRecipientsEmails().size() > 0) {
            ride = createAcceptedRideForMultiplePassengers.execute(requestedRideRequest, assignedDriver, sender);
        } else {
            ride = createAcceptedRideForOnePassenger.execute(requestedRideRequest, assignedDriver, sender);
        }
        return addChargeToRide.execute(ride, sender.getCustomerId(), requestedRideRequest.getPaymentMethodId());
    }
}
