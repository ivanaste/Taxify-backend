package com.kts.taxify.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
	@Email
	@NotEmpty
	String email;

	@NotEmpty
	private String name;

	@NotEmpty
	private String surname;
}
