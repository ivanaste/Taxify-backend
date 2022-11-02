package com.kts.taxify.services.user;

import com.kts.taxify.exception.UserNotFoundException;
import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByEmail {
	private final UserRepository userRepository;

	public User execute(final String email) {
		return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
	}
}
