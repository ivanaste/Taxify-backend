package com.kts.taxify.services.simulations;

import com.kts.taxify.exception.NoActiveDriversException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.kts.taxify.services.ride.GetDriverAssignedRide;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetClosestUnoccupiedDriver {
	private final DriverRepository driverRepository;

	private final RideRepository rideRepository;

	private final GetSelf getSelf;
	private final SimulationService simulationService;

	@Transactional
	public Driver execute(Location clientLocation) throws InterruptedException, ExecutionException {
		String city = getSelf.execute().getCity();
		List<Driver> activeDriversInCity = driverRepository.findAllByCityAndActiveIsTrueAndReservedIsFalse(city);
		Driver closestDriver = null;
		Double closestDistance = Double.MAX_VALUE;
		List<Driver> currentlyUnoccupied = activeDriversInCity.stream().filter(driver -> !driver.getVehicle().getOccupied()).toList();
		if (currentlyUnoccupied.isEmpty()) {
			closestDriver = simulationService.searchForFirstFreeDriver(getSelf.execute().getEmail());
			Ride activeRide = rideRepository.findFirstByDriverAndStatusIn(closestDriver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION));
			while (activeRide != null) {
				Thread.sleep(2000);
				activeRide = rideRepository.findFirstByDriverAndStatusIn(closestDriver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION));

			}
			return closestDriver;
		}
		else {
			for (Driver driver : currentlyUnoccupied) {
				if (driver.getVehicle().getLocation().distanceTo(clientLocation) < closestDistance) {
					closestDriver = driver;
					closestDistance = driver.getVehicle().getLocation().distanceTo(clientLocation);
				}
			}
			return closestDriver;
		}
	}
}
