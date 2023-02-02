package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.exception.InvalidPaymentForSharedRideException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.checkout.CheckoutRide;
import com.kts.taxify.services.ride.CreateAcceptedRide;
import com.kts.taxify.services.simulations.GetClosestUnoccupiedDriver;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class FindSuitableDriver {
    private final GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;

    private final NotifyDriver notifyDriver;

    private final CreateAcceptedRide createAcceptedRide;

    private final SetDriverVehicleAssOccupied setDriverVehicleAssOccupied;

    private final CheckoutRide checkoutRide;

    public DriverResponse execute(RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException, StripeException {
        Driver closestDriver = getClosestUnoccupiedDriver.execute(requestedRideRequest.getClientLocation());
        setDriverVehicleAssOccupied.execute(closestDriver);
        Ride assignedRide = createAcceptedRide.execute(requestedRideRequest, closestDriver);
        if (checkoutRide.execute(assignedRide)) {
            notifyDriver.execute(closestDriver.getEmail(), NotificationType.RIDE_ASSIGNED);
            return DriverConverter.toDriverWithAssignedRideResponse(closestDriver, assignedRide);
        }
        throw new InvalidPaymentForSharedRideException();
    }
}
