package com.kts.taxify.controller;

import com.kts.taxify.dto.request.driver.CreateDriverRequest;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.driver.CreateDriver;
import com.kts.taxify.services.driver.FindSuitableDriver;
import com.kts.taxify.services.driver.GetActiveDriversInArea;
import com.kts.taxify.services.driver.GetDriverInfo;
import com.kts.taxify.services.driver.MakeDriverActive;
import com.kts.taxify.services.driver.MakeDriverInactive;
import com.kts.taxify.services.driverTimetable.GetDriverRemainingWorkTime;

import java.io.IOException;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

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

	@PostMapping("/create")
	@HasAnyPermission({ Permission.REGISTER_DRIVER })
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

	@GetMapping("/get/{email}")
	@HasAnyPermission({ Permission.GET_DRIVER_INFO })
	public DriverResponse getDriverInfo(@PathVariable String email) {
		return getDriverInfo.execute(email);
	}

	@GetMapping("/remainingWorkTime/{email}")
	public long getDriverRemainingTimeToWorkToday(@PathVariable("email") String email) {
		return getRemainingWorkTime.execute(email);
	}

	@PutMapping("/goOffline/{email}")
	@HasAnyPermission({ Permission.SET_DRIVER_INACTIVE })
	public DriverResponse makeDriverOffline(@PathVariable("email") String email) {
		return makeDriverInactive.execute(email);
	}

	@PutMapping("/goOnline/{email}")
	@HasAnyPermission({ Permission.SET_DRIVER_INACTIVE })
	public DriverResponse makeDriverOnline(@PathVariable("email") String email) {
		return makeDriverActive.execute(email);
	}

	@PostMapping(value = "/suitableDriverForRide")
	@HasAnyPermission({ Permission.FIND_SUITABLE_DRIVER })
	public DriverResponse getSuitableDriverForRide(@RequestBody final RequestedRideRequest requestedRideRequest) throws IOException, InterruptedException {
		return findSuitableDriver.execute(requestedRideRequest);
	}
}
