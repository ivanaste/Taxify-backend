package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.ride.CreateAcceptedRide;
import com.kts.taxify.services.simulations.GetClosestUnoccupiedDriver;

import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindSuitableDriver {
	private final GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;

	private final NotifyDriver notifyDriver;

	private final CreateAcceptedRide createAcceptedRide;

	private final SetDriverVehicleAssOccupied setDriverVehicleAssOccupied;

	public DriverResponse execute(RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException {
		Driver closestDriver = getClosestUnoccupiedDriver.execute(requestedRideRequest.getClientLocation());
		setDriverVehicleAssOccupied.execute(closestDriver);
		Ride assignedRide = createAcceptedRide.execute(requestedRideRequest, closestDriver);
		notifyDriver.execute(closestDriver.getEmail(), NotificationType.RIDE_ASSIGNED);
		return DriverConverter.toDriverWithAssignedRideResponse(closestDriver, assignedRide);
	}
}
