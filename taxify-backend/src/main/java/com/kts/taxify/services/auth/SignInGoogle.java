package com.kts.taxify.services.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.exception.InvalidGoogleAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class SignInGoogle {
    private final LogInUser logInUser;
    private final GoogleIdTokenVerifier verifier;


    public AuthTokenResponse execute(String credentials) throws GeneralSecurityException, IOException {
        try {
            GoogleIdToken idToken = verifier.verify(credentials);
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String password = payload.getSubject();
            return logInUser.execute(email, password);
        } catch (Exception e) {
            throw new InvalidGoogleAccountException();
        }
    }
}
