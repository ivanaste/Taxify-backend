package com.kts.taxify.controller;

import com.kts.taxify.dto.request.driver.CreateDriverRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.services.driver.CreateDriver;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
	private final CreateDriver createDriver;

	@PostMapping("/create")
	public UserResponse createDriver(@Valid @RequestBody final CreateDriverRequest createDriverRequest) {
		return createDriver.execute(createDriverRequest);
	}
}
