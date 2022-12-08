package com.kts.taxify.services.password;

import com.kts.taxify.exception.PasswordSameException;
import com.kts.taxify.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckIsPasswordDifferent {
    private final PasswordEncoder passwordEncoder;

    public void execute(String password, User user) {
        if (passwordEncoder.matches(password, user.getPasswordHash())) throw new PasswordSameException();
    }
}