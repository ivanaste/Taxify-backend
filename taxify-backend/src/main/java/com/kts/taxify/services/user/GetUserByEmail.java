package com.kts.taxify.services.user;

import com.kts.taxify.exception.UserNotFoundException;
import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GetUserByEmail {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User execute(final String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
