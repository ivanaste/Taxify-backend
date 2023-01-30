package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetActiveDriversInArea {

    private final DriverRepository driverRepository;

    public Collection<DriverResponse> execute(final Double minLongitude,
                                              final Double maxLongitude,
                                              final Double minLatitude,
                                              final Double maxLatitude) {
        Set<DriverResponse> driversInArea = new HashSet<>();
        driverRepository.findAllByActive(true).forEach(driver -> {
            final Vehicle vehicle = driver.getVehicle();
            if (vehicle.isInArea(minLongitude, maxLongitude, minLatitude, maxLatitude)) {
                driversInArea.add(DriverConverter.toDriverResponse(driver));
            }
        });
        return driversInArea;
    }
}
