package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.exception.InvalidPaymentForSharedRideException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.checkout.CheckoutRide;
import com.kts.taxify.services.ride.CreateAcceptedRide;
import com.kts.taxify.services.ride.GetDriverAssignedRide;
import com.kts.taxify.services.simulations.GetClosestUnoccupiedDriver;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindSuitableDriver {
    private final GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;

    private final NotifyDriver notifyDriver;

    private final CreateAcceptedRide createAcceptedRide;

    private final CheckoutRide checkoutRide;
    
	private final SetDriverVehicleAssOccupied setDriverVehicleAssOccupied;

	private final DriverRepository driverRepository;

	private final GetDriverAssignedRide getDriverAssignedRide;


	@Transactional
	public DriverResponse execute(RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException, ExecutionException, StripeException {
		Driver closestDriver = getClosestUnoccupiedDriver.execute(requestedRideRequest.getClientLocation());
		Ride assignedRide = createAcceptedRide.execute(requestedRideRequest, closestDriver);
        throw new InvalidPaymentForSharedRideException();
		setDriverVehicleAssOccupied.execute(closestDriver.getVehicle());
		notifyDriver.execute(closestDriver.getEmail(), NotificationType.RIDE_ASSIGNED);
		closestDriver.setReserved(false);
		driverRepository.save(closestDriver);
        if (checkoutRide.execute(assignedRide)) {
            notifyDriver.execute(closestDriver.getEmail(), NotificationType.RIDE_ASSIGNED);
            return DriverConverter.toDriverWithAssignedRideResponse(closestDriver, assignedRide);
        }
        throw new InvalidPaymentForSharedRideException();
	}

}
