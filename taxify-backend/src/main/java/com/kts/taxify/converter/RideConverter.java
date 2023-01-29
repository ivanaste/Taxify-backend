package com.kts.taxify.converter;

import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Ride;
import org.modelmapper.ModelMapper;

public class RideConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static RideResponse toRideResponse(final Ride ride) {
        return modelMapper.map(ride, RideResponse.class);
    }
}
