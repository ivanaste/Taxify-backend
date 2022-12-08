package com.kts.taxify.services.password;


import com.kts.taxify.exception.PasswordMismatchException;
import org.springframework.stereotype.Service;

@Service
public class CheckPasswordConfirmation {

    public void execute(String password, String passwordConfirmation) {
        if (!(password.equals(passwordConfirmation))) throw new PasswordMismatchException();
    }

}
