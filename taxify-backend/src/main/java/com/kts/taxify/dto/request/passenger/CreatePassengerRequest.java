package com.kts.taxify.dto.request.passenger;

import com.kts.taxify.model.UserRole;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePassengerRequest {
	String email;

	UserRole role;

	String password;

	String name;

	String surname;

	String city;

	String phoneNumber;

	String profilePicture;
}
