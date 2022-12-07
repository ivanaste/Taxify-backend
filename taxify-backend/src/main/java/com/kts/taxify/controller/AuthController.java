package com.kts.taxify.controller;

import com.kts.taxify.dto.request.auth.LoginRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.auth.LogInUser;
import com.kts.taxify.services.auth.SignInWithGoogle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LogInUser loginUser;
    private final GetSelf getSelf;
    private final SignInWithGoogle logInGoogle;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthTokenResponse login(@Valid @RequestBody final LoginRequest loginRequest) {
        return new AuthTokenResponse(loginUser.execute(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/self")
    public UserResponse getSelf() {
        return getSelf.execute();
    }


    @PostMapping("/login-google/{credentials}")
    public AuthTokenResponse loginGoogle(@PathVariable("credentials") String credentials) throws GeneralSecurityException, IOException {
        return new AuthTokenResponse(logInGoogle.execute(credentials));
    }
}
