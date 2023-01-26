package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.services.driverTimetable.GetDriverRemainingWorkTime;
import com.kts.taxify.services.driverTimetable.UpdateDriverTimetable;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.services.user.SaveUser;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MakeDriverInactive {
	private final GetUserByEmail getUserByEmail;

	private final SaveUser saveUser;
	private final UpdateDriverTimetable updateDriverTimetable;

	private final GetDriverRemainingWorkTime getDriverRemainingWorkTime;

	public DriverResponse execute(String email) {
		Driver driver = (Driver) getUserByEmail.execute(email);
		if (!updateDriverTimetable.execute(email)) {
			return null;
		}
		driver.setActive(false);
		saveUser.execute(driver);
		DriverResponse driverRes = DriverConverter.toDriverResponse(driver);
		driverRes.setRemainingWorkTime(getDriverRemainingWorkTime.execute(email));
		return driverRes;
	}
}
