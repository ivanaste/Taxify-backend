package com.kts.taxify.converter;

import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.Driver;
import org.modelmapper.ModelMapper;

public class DriverConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static DriverResponse toDriverResponse(final Driver driver) {
        DriverResponse driverResponse = modelMapper.map(driver, DriverResponse.class);
        final VehicleResponse vehicleResponse = VehicleConverter.toVehicleResponse(driver.getVehicle());
        driverResponse.setVehicle(vehicleResponse);
        driverResponse.setRole(driver.getRole().getName());
        return driverResponse;
    }
}
