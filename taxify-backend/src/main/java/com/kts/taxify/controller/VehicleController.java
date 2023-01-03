package com.kts.taxify.controller;

import com.kts.taxify.dto.request.vehicle.ChangeVehicleLocationRequest;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.services.vehicle.ChangeVehicleLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final ChangeVehicleLocation changeVehicleLocation;

    @PutMapping("/location")
    public VehicleResponse changeVehicleLocation(@RequestBody final ChangeVehicleLocationRequest changeVehicleLocationRequest) {
        return changeVehicleLocation.execute(changeVehicleLocationRequest);
    }
}
