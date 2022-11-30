package com.kts.taxify.services.user;

import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SaveUser {

    private final UserRepository userRepository;

    @Transactional
    public User execute(final User user) {
        return userRepository.save(user);
    }
}
