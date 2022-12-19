package com.kts.taxify.converter;

import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.Vehicle;
import org.modelmapper.ModelMapper;

public class VehicleConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static VehicleResponse toVehicleResponse(final Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleResponse.class);
    }
}
