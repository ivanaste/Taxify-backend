package com.kts.taxify.services.driver;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import com.kts.taxify.repository.DriverRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUnoccupiedScheduledDrivers {

	private final DriverRepository driverRepository;

	public List<Driver> execute(String city) {
		List<Driver> unoccupiedScheduledDrivers = new ArrayList<>();

		List<Driver> activeDriversInCity = driverRepository.findAllByCityAndActiveIsTrue(city);
		for (Driver driver : activeDriversInCity) {
			Set<Ride> rides = driver.getRides();
			for (Ride ride : rides) {
				if (ride.getScheduledAt() == null) {
					unoccupiedScheduledDrivers.add(driver);
				}
			}
		}
		return unoccupiedScheduledDrivers;
	}
}
