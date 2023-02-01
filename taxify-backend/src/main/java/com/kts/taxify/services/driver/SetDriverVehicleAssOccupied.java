package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SetDriverVehicleAssOccupied {
    private final SaveUser saveUser;

    public void execute(Driver driver) throws IOException, InterruptedException {
        driver.getVehicle().setOccupied(true);
        saveUser.execute(driver);
    }
}
