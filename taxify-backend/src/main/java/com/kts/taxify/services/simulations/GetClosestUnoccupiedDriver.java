package com.kts.taxify.services.simulations;

import com.kts.taxify.exception.NoActiveDriversException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.driver.GetUnoccupiedScheduledDrivers;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetClosestUnoccupiedDriver {
	private final DriverRepository driverRepository;

	private final GetUnoccupiedScheduledDrivers getUnoccupiedScheduledDrivers;

	private final GetSelf getSelf;

	@Transactional(readOnly = true)
	public Driver execute(Location clientLocation) {
		String city = getSelf.execute().getCity();
		List<Driver> activeDriversInCity = driverRepository.findAllByCityAndActiveIsTrue(city);
		if (activeDriversInCity.isEmpty()) {
			throw new NoActiveDriversException();
		}

		Driver closestDriver = null;
		Double closestDistance = Double.MAX_VALUE;
		List<Driver> currentlyUnoccupied = activeDriversInCity.stream().filter(driver -> !driver.getVehicle().getOccupied()).toList();
		if (currentlyUnoccupied.isEmpty()) {
			throw new NoActiveDriversException();

		}

		for (Driver driver : currentlyUnoccupied) {
			if (driver.getVehicle().getLocation().distanceTo(clientLocation) < closestDistance) {
				closestDriver = driver;
				closestDistance = driver.getVehicle().getLocation().distanceTo(clientLocation);
			}
		}
		return closestDriver;
	}
}
