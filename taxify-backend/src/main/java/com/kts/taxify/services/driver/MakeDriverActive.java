package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.services.driverTimetable.CreateDriverTimetable;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.services.user.SaveUser;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MakeDriverActive {
	private final GetUserByEmail getUserByEmail;

	private final SaveUser saveUser;
	private final CreateDriverTimetable createDriverTimetable;

	public DriverResponse execute(String email) {
		Driver driver = (Driver) getUserByEmail.execute(email);
		createDriverTimetable.execute(driver);
		driver.setActive(true);
		driver = (Driver) saveUser.execute(driver);
		return DriverConverter.toDriverResponse(driver);
	}
}
