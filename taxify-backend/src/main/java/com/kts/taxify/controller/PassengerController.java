package com.kts.taxify.controller;

import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.services.passenger.CreatePassenger;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {
	private final CreatePassenger createPassenger;

	@PostMapping("/create")
	public UserResponse createPassenger(@Valid @RequestBody final CreatePassengerRequest createPassengerRequest) {
		return createPassenger.execute(createPassengerRequest);
	}
}
