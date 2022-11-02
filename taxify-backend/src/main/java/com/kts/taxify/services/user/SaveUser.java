package com.kts.taxify.services.user;

import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveUser {

	private final UserRepository userRepository;

	public User execute(final User user) {
		return userRepository.save(user);
	}
}
