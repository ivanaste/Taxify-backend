package com.kts.taxify.services.user;

import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveUser {

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public User execute(final User user) {
        return userRepository.save(user);
    }
}
