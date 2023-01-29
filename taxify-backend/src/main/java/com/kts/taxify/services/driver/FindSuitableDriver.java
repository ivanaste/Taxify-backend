package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.services.ride.CreateRide;
import com.kts.taxify.services.simulations.GetClosestUnoccupiedDriver;
import com.kts.taxify.services.simulations.SimulateVehicleToClient;

import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindSuitableDriver {

	private final DriverRepository driverRepository;

	private final GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;

	private final SimulateVehicleToClient simulateVehicleToClient;

	private final NotifyDriverOfAssignedRide notifyDriverOfAssignedRide;

	private final CreateRide createRide;

	public DriverResponse execute(RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException {
		Driver closestDriver = getClosestUnoccupiedDriver.execute(requestedRideRequest.getClientLocation());
		createRide.execute(requestedRideRequest, closestDriver);
		notifyDriverOfAssignedRide.execute(closestDriver.getEmail());
		return DriverConverter.toDriverResponse(closestDriver);
	}
}
