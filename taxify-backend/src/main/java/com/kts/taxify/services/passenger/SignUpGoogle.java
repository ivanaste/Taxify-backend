package com.kts.taxify.services.passenger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.request.passenger.GoogleSignupRequest;
import com.kts.taxify.exception.InvalidGoogleAccountException;
import com.kts.taxify.exception.UserAlreadyExistsException;
import com.kts.taxify.model.AccountProvider;
import com.kts.taxify.services.auth.LogInUser;
import com.kts.taxify.services.user.UserExistsByEmail;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SignUpGoogle {
    private final LogInUser logInUser;
    private final UserExistsByEmail userExistsByEmail;
    private final CreatePassenger createPassenger;
    private final GoogleIdTokenVerifier verifier;


    public String execute(GoogleSignupRequest googleSignupRequest) throws GeneralSecurityException, IOException {
        try {
            GoogleIdToken idToken = verifier.verify(googleSignupRequest.getCredentials());
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String password = payload.getSubject();
            if (!userExistsByEmail.execute(email)) {
                createPassenger.execute(constructPassengerRequest(payload, googleSignupRequest), AccountProvider.GOOGLE);
                return logInUser.execute(email, password);
            } else throw new UserAlreadyExistsException();
        } catch (Exception e) {
            throw new InvalidGoogleAccountException();
        }
    }

    private CreatePassengerRequest constructPassengerRequest(Payload payload, GoogleSignupRequest googleSignupRequest) {
        return CreatePassengerRequest.builder()
                .email(payload.getEmail())
                .password(payload.getSubject())
                .name((String) payload.get("given_name"))
                .surname(!Objects.isNull(payload.get("family_name")) ? (String) payload.get("family_name") : Strings.EMPTY)
                .city(googleSignupRequest.getCity())
                .phoneNumber(googleSignupRequest.getPhoneNumber())
                .profilePicture(Strings.EMPTY)
                .build();
    }
}
