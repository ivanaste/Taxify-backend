package com.kts.taxify.dto.request.driver;

import com.kts.taxify.model.UserRole;
import com.kts.taxify.model.VehicleType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDriverRequest {
	String email;

	UserRole role;

	String password;

	String name;

	String surname;

	String city;

	String phoneNumber;

	String profilePicture;

	//initial vehicle location

	String vehicleBrand;

	String vehicleModel;

	Integer vehicleHorsePower;

	VehicleType vehicletype;

	Boolean babyFriendly;

	Boolean petFriendly;
}
