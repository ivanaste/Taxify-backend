package com.kts.taxify.controller;

import com.kts.taxify.dto.request.driver.CreateDriverRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.driver.CreateDriver;
import com.kts.taxify.services.driver.GetActiveDriversInArea;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final CreateDriver createDriver;

    private final GetActiveDriversInArea getActiveDriversInArea;

    @PostMapping("/create")
    @HasAnyPermission({Permission.REGISTER_DRIVER})
    public UserResponse createDriver(@RequestBody final CreateDriverRequest createDriverRequest) {
        return createDriver.execute(createDriverRequest);
    }

    @GetMapping("/allActiveInArea")
    public Collection<DriverResponse> getVehiclesInArea(@RequestParam final Double minLongitude,
                                                        @RequestParam final Double maxLongitude,
                                                        @RequestParam final Double minLatitude,
                                                        @RequestParam final Double maxLatitude) {
        return getActiveDriversInArea.execute(minLongitude, maxLongitude, minLatitude, maxLatitude);
    }
}
