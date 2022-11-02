package com.kts.taxify.controller;

import com.kts.taxify.dto.request.auth.LoginRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.auth.LogInUser;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final LogInUser loginUser;
	private final GetSelf getSelf;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public AuthTokenResponse login(@Valid @RequestBody final LoginRequest loginRequest) {
		return new AuthTokenResponse(loginUser.execute(loginRequest.getEmail(), loginRequest.getPassword()));
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/self")
	public UserResponse getSelf() {
		return getSelf.execute();
	}
}
