package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.VehicleRepository;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SetDriverVehicleAssOccupied {
    private final VehicleRepository vehicleRepository;

    @Transactional
    public void execute(Vehicle vehicle) throws IOException, InterruptedException {
        vehicle.setOccupied(true);
        vehicleRepository.save(vehicle);
    }
}
