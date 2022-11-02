package com.kts.taxify.services.user;

import com.kts.taxify.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserExistsByEmail {

	private final UserRepository userRepository;

	public Boolean execute(final String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}
