package com.kts.taxify.services.driver;

import com.kts.taxify.converter.DriverConverter;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.services.user.GetUserByEmail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetDriverInfo {

	private final GetUserByEmail getUserByEmail;

	public DriverResponse execute(String email) {
		Driver driver = (Driver) getUserByEmail.execute(email);
		return DriverConverter.toDriverResponse(driver);
	}
}
