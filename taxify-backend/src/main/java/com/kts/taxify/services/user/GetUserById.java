package com.kts.taxify.services.user;

import com.kts.taxify.exception.UserNotFoundException;
import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserById {
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User execute(final UUID id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}
}
