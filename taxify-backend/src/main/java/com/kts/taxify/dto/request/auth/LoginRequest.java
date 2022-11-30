package com.kts.taxify.dto.request.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String password;
}
