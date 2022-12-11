package com.kts.taxify.controller;

import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.request.passenger.FacebookSignupRequest;
import com.kts.taxify.dto.request.passenger.GoogleSignupRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.AccountProvider;
import com.kts.taxify.services.passenger.CreatePassenger;
import com.kts.taxify.services.passenger.SignUpFacebook;
import com.kts.taxify.services.passenger.SignUpGoogle;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {
    private final CreatePassenger createPassenger;
    private final SignUpFacebook signUpFacebook;
    private final SignUpGoogle signUpGoogle;

    @PostMapping("/create")
    public UserResponse createPassenger(@Valid @RequestBody final CreatePassengerRequest createPassengerRequest) {
        return createPassenger.execute(createPassengerRequest, AccountProvider.LOCAL);
    }

    @PostMapping("/facebook-signup")
    public AuthTokenResponse signupFacebook(@Valid @RequestBody final FacebookSignupRequest facebookSignUpRequest) {
        return new AuthTokenResponse(signUpFacebook.execute(facebookSignUpRequest));
    }

    @PostMapping("/google-signup")
    public AuthTokenResponse signupGoogle(@Valid @RequestBody final GoogleSignupRequest googleSignupRequest) throws GeneralSecurityException, IOException {
        return new AuthTokenResponse(signUpGoogle.execute(googleSignupRequest));
    }
}
