package com.kts.taxify.converter;

import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import org.modelmapper.ModelMapper;

public class DriverConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static DriverResponse toDriverResponse(final Driver driver) {
        DriverResponse driverResponse = modelMapper.map(driver, DriverResponse.class);
        final VehicleResponse vehicleResponse = VehicleConverter.toVehicleResponse(driver.getVehicle());
        driverResponse.setVehicle(vehicleResponse);
        return driverResponse;
    }

    public static DriverResponse toDriverWithAssignedRideResponse(final Driver driver, final Ride ride) {
        DriverResponse driverResponse = modelMapper.map(driver, DriverResponse.class);
        final VehicleResponse vehicleResponse = VehicleConverter.toVehicleResponse(driver.getVehicle());
        final RideResponse rideResponse = new RideResponse(ride.getId(), ride.getSender());
        driverResponse.setVehicle(vehicleResponse);
        driverResponse.setRide(rideResponse);
        return driverResponse;
    }
}
