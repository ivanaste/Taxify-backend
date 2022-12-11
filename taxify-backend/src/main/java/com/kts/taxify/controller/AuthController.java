package com.kts.taxify.controller;

import com.kts.taxify.dto.request.auth.LoginRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.auth.LogInUser;
import com.kts.taxify.services.auth.SignInGoogle;
import com.kts.taxify.services.auth.SignInWithFacebook;
import com.kts.taxify.services.user.UserExistsByEmail;
import com.kts.taxify.services.user.UserSignedWithGoogleExists;
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
    private final SignInGoogle signInWithGoogle;
    private final SignInWithFacebook signInWithFacebook;
    private final UserExistsByEmail userExistsByEmail;
    private final UserSignedWithGoogleExists userSignedWithGoogleExists;

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
        return new AuthTokenResponse(signInWithGoogle.execute(credentials));
    }

    @GetMapping("/user-exists/{email}")
    public Boolean userExists(@PathVariable("email") String email) {
        return userExistsByEmail.execute(email);
    }

    @GetMapping("/user-signed-with-google-exists/{credentials}")
    public Boolean userSignedWithGoogleExists(@PathVariable("credentials") String credentials) throws GeneralSecurityException, IOException {
        return userSignedWithGoogleExists.execute(credentials);
    }

}
