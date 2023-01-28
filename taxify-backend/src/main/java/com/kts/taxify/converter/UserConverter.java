package com.kts.taxify.converter;

import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.User;

import org.modelmapper.ModelMapper;

public class UserConverter {
	private final static ModelMapper modelMapper = new ModelMapper();

	public static UserResponse toUserResponse(final User user) {
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);
		userResponse.setRole(user.getRole().getName());
		return userResponse;
	}
}
