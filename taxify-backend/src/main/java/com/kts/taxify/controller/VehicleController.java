package com.kts.taxify.controller;

import com.kts.taxify.dto.request.vehicle.ChangeVehicleLocationRequest;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.services.vehicle.ChangeVehicleLocation;
import com.kts.taxify.services.vehicle.GetVehiclesInArea;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final ChangeVehicleLocation changeVehicleLocation;

    private final GetVehiclesInArea getVehiclesInArea;

    @PutMapping("/location")
    public VehicleResponse changeVehicleLocation(@RequestBody final ChangeVehicleLocationRequest changeVehicleLocationRequest) {
        return changeVehicleLocation.execute(changeVehicleLocationRequest);
    }

    @GetMapping("/allInArea")
    public Collection<VehicleResponse> getVehiclesInArea(@RequestParam final Double minLongitude,
                                                         @RequestParam final Double maxLongitude,
                                                         @RequestParam final Double minLatitude,
                                                         @RequestParam final Double maxLatitude) {
        return getVehiclesInArea.execute(minLongitude, maxLongitude, minLatitude, maxLatitude);
    }
}
