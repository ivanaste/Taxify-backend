package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.ride.CreateAcceptedRide;
import com.kts.taxify.services.simulations.GetClosestUnoccupiedDriver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FindSuitableDriver {
	private final GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;

	private final NotifyDriverOfAssignedRide notifyDriverOfAssignedRide;

	private final CreateAcceptedRide createAcceptedRide;

	private final SetDriverVehicleAssOccupied setDriverVehicleAssOccupied;

	public DriverResponse execute(RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException {
		Driver closestDriver = getClosestUnoccupiedDriver.execute(requestedRideRequest.getClientLocation());
		setDriverVehicleAssOccupied.execute(closestDriver);
		Ride assignedRide = createAcceptedRide.execute(requestedRideRequest, closestDriver);
		notifyDriverOfAssignedRide.execute(closestDriver.getEmail());

		return DriverConverter.toDriverWithAssignedRideResponse(closestDriver, assignedRide);
	}
}
