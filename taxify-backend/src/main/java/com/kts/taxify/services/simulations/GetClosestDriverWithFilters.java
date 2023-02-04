package com.kts.taxify.services.simulations;

import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetClosestDriverWithFilters {
	private final DriverRepository driverRepository;

	private final RideRepository rideRepository;

	private final GetSelf getSelf;
	private final SimulationService simulationService;

	@Transactional
	public Driver execute(RequestedRideRequest requestedRideRequest) throws InterruptedException, ExecutionException {
		String city = getSelf.execute().getCity();
		List<Driver> activeDriversInCity = driverRepository.findAllByCityAndActiveIsTrueAndReservedIsFalse(city);
		Driver closestDriver = null;
		Double closestDistance = Double.MAX_VALUE;
		List<Driver> currentlyUnoccupied = getCurrentlyUnoccupiedDriversWithFilters(requestedRideRequest, activeDriversInCity);
		if (currentlyUnoccupied.isEmpty()) {
			closestDriver = simulationService.searchForFirstFreeDriver(requestedRideRequest);
			Ride activeRide = rideRepository.findFirstByDriverAndStatusIn(closestDriver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION));
			while (activeRide != null) {
				Thread.sleep(2000);
				activeRide = rideRepository.findFirstByDriverAndStatusIn(closestDriver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION));
			}
			return closestDriver;
		}
		else {
			for (Driver driver : currentlyUnoccupied) {
				if (driver.getVehicle().getLocation().distanceTo(requestedRideRequest.getClientLocation()) < closestDistance) {
					closestDriver = driver;
					closestDistance = driver.getVehicle().getLocation().distanceTo(requestedRideRequest.getClientLocation());
				}
			}
			return closestDriver;
		}
	}

	private List<Driver> getCurrentlyUnoccupiedDriversWithFilters(RequestedRideRequest requestedRideRequest, List<Driver> activeDriversInCity) {
		List<Driver> currentlyUnoccupied = new ArrayList<>();
		for(Driver driver: activeDriversInCity) {
			if (!driver.getVehicle().getOccupied() && requestedRideRequest.getVehicleTypes().contains((driver.getVehicle().getType().getName()))) {
				if (requestedRideRequest.isBabyFriendly() && requestedRideRequest.isPetFriendly()) {
					if (driver.getVehicle().getBabyFriendly() && driver.getVehicle().getPetFriendly()) {
						currentlyUnoccupied.add(driver);
					}
				}
				else if (requestedRideRequest.isBabyFriendly()) {
					if (driver.getVehicle().getBabyFriendly()) {
						currentlyUnoccupied.add(driver);
					}
				}
				else if (requestedRideRequest.isPetFriendly()) {
					if (driver.getVehicle().getPetFriendly()) {
						currentlyUnoccupied.add(driver);
					}
				}
				else {
					currentlyUnoccupied.add(driver);
				}
			}
		}
		return currentlyUnoccupied;
	}
}
