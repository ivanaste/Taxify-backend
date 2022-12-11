package com.kts.taxify.services.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class UserSignedWithGoogleExists {
    private final UserExistsByEmail userExistsByEmail;
    private final GoogleIdTokenVerifier verifier;

    public Boolean execute(final String credentials) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(credentials);
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        return userExistsByEmail.execute(email);
    }
}
