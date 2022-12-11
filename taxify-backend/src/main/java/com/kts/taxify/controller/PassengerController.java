package com.kts.taxify.controller;

import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.request.passenger.FacebookSignupRequest;
import com.kts.taxify.dto.request.passenger.GoogleSignupRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.AccountProvider;
import com.kts.taxify.services.passenger.ActivateEmail;
import com.kts.taxify.services.passenger.CreatePassenger;
import com.kts.taxify.services.passenger.SignUpFacebook;
import com.kts.taxify.services.passenger.SignUpGoogle;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    private final ActivateEmail activateEmail;

    @PostMapping("/create")
    public UserResponse createPassenger(@Valid @RequestBody final CreatePassengerRequest createPassengerRequest) {
        return createPassenger.execute(createPassengerRequest, AccountProvider.LOCAL);
    }

    @PostMapping("/facebook-signup")
    public AuthTokenResponse signupFacebook(@Valid @RequestBody final FacebookSignupRequest facebookSignUpRequest) {
        return signUpFacebook.execute(facebookSignUpRequest);
    }

    @PostMapping("/google-signup")
    public AuthTokenResponse signupGoogle(@Valid @RequestBody final GoogleSignupRequest googleSignupRequest) throws GeneralSecurityException, IOException {
        return signUpGoogle.execute(googleSignupRequest);
    }

    @PutMapping("/activateEmail/{token}")
    public void activateEmail(@PathVariable("token") final String token) {
        activateEmail.execute(token);
    }
}
