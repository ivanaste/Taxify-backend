package com.kts.taxify.dto.response;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse extends UserResponse {

	@NotEmpty
	Boolean active;

	@NotEmpty
	Long remainingWorkTime;

	@NotEmpty
	VehicleResponse vehicle;
}
