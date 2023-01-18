package com.kts.taxify.services.simulations;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetClosestUnoccupiedDriver {
    private final DriverRepository driverRepository;


    @Transactional(readOnly = true)
    public Driver execute(String city, Location location) {
        List<Driver> driversInCity = driverRepository.findAllByCityAndActiveIsTrue(city);
        Driver closestDriver = null;
        Double closestDistance = Double.MAX_VALUE;
        for (Driver driver : driversInCity.stream().filter(driver -> !driver.getVehicle().getOccupied()).toList()) {
            if (driver.getVehicle().getLocation().distanceTo(location) < closestDistance) {
                closestDriver = driver;
                closestDistance = driver.getVehicle().getLocation().distanceTo(location);
            }
        }
        return closestDriver;
    }
}
