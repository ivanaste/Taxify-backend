package com.kts.taxify.services.vehicle;

import com.kts.taxify.converter.VehicleConverter;
import com.kts.taxify.dto.request.vehicle.ChangeVehicleLocationRequest;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ChangeVehicleLocation {

    private final GetVehicleById getVehicleById;

    private final SaveVehicle saveVehicle;

    private final NotifyFrontendOfVehicleLocationChange notifyFrontendOfVehicleLocationChange;

    public VehicleResponse execute(@Valid final ChangeVehicleLocationRequest changeVehicleLocationRequest) {
        Vehicle vehicle = getVehicleById.execute(changeVehicleLocationRequest.getId());
        vehicle.setLocation(changeVehicleLocationRequest.getLocation());
        notifyFrontendOfVehicleLocationChange.execute();
        return VehicleConverter.toVehicleResponse(saveVehicle.execute(vehicle));
    }
}