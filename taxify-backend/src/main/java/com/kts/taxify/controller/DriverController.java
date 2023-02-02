package com.kts.taxify.controller;

import com.kts.taxify.converter.RideConverter;
import com.kts.taxify.dto.request.driver.CreateDriverRequest;
import com.kts.taxify.dto.request.ride.RejectRideRequest;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.driver.*;
import com.kts.taxify.services.driverTimetable.GetDriverRemainingWorkTime;
import com.kts.taxify.services.ride.FinishRide;
import com.kts.taxify.services.ride.GetDriverAssignedRide;
import com.kts.taxify.services.ride.RejectRide;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
>>>>>>>Stashed changes

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final CreateDriver createDriver;

    private final GetActiveDriversInArea getActiveDriversInArea;

    private final GetDriverInfo getDriverInfo;

    private final GetDriverRemainingWorkTime getRemainingWorkTime;

    private final MakeDriverInactive makeDriverInactive;

    private final MakeDriverActive makeDriverActive;

    private final FindSuitableDriver findSuitableDriver;

    private final GetDriverAssignedRide getDriverAssignedRide;

    private final FinishRide finishRide;

    private final RejectRide rejectRide;

    @PostMapping("/create")
    @HasAnyPermission({Permission.REGISTER_DRIVER})
    public UserResponse createDriver(@RequestBody CreateDriverRequest createDriverRequest) {
        return createDriver.execute(createDriverRequest);
    }

    @GetMapping("/allActiveInArea")
    public Collection<DriverResponse> getVehiclesInArea(@RequestParam Double minLongitude,
                                                        @RequestParam Double maxLongitude,
                                                        @RequestParam Double minLatitude,
                                                        @RequestParam Double maxLatitude) {
        return getActiveDriversInArea.execute(minLongitude, maxLongitude, minLatitude, maxLatitude);
    }

    @GetMapping("/get/{email}")
    @HasAnyPermission({Permission.GET_DRIVER_INFO})
    public DriverResponse getDriverInfo(@PathVariable String email) {
        return getDriverInfo.execute(email);
    }

    @GetMapping("/remainingWorkTime/{email}")
    public long getDriverRemainingTimeToWorkToday(@PathVariable("email") String email) {
        return getRemainingWorkTime.execute(email);
    }

    @PutMapping("/goOffline/{email}")
    @HasAnyPermission({Permission.SET_DRIVER_INACTIVE})
    public DriverResponse makeDriverOffline(@PathVariable("email") String email) {
        return makeDriverInactive.execute(email);
    }

    @PutMapping("/goOnline/{email}")
    @HasAnyPermission({Permission.SET_DRIVER_INACTIVE})
    public DriverResponse makeDriverOnline(@PathVariable("email") String email) {
        return makeDriverActive.execute(email);
    }

    @PostMapping(value = "/suitableDriverForRide")
    @HasAnyPermission({Permission.FIND_SUITABLE_DRIVER})
    public DriverResponse getSuitableDriverForRide(@RequestBody RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException, StripeException {
        return findSuitableDriver.execute(requestedRideRequest);
    }

    @GetMapping(value = "/assignedRide")
    @HasAnyPermission({Permission.GET_ASSIGNED_RIDE})
    public RideResponse getAssignedRide() {
        return RideConverter.toRideResponse(getDriverAssignedRide.execute());
    }

    @PutMapping("/finishRide")
    @HasAnyPermission({Permission.FINISH_RIDE})
    public void finishRide() {
        finishRide.execute();
    }

    @PutMapping("/rejectRide")
    @HasAnyPermission({Permission.REJECT_RIDE})
    public void rejectRide(@RequestBody RejectRideRequest rejectRideRequest) {
        rejectRide.execute(rejectRideRequest.getRejectionReason());
    }
}
