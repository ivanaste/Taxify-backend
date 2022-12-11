package com.kts.taxify.services.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.kts.taxify.services.passenger.CreatePassenger;
import com.kts.taxify.services.user.UserExistsByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class SignInWithFacebook {
    private final LogInUser logInUser;
    private final UserExistsByEmail userExistsByEmail;
    private final CreatePassenger createPassenger;
    private final GoogleIdTokenVerifier verifier;


    public String execute(String accessToken) throws GeneralSecurityException, IOException {
        /*Facebook facebook = new FacebookFactory().getInstance();
        facebook.
                GoogleIdToken idToken = verifier.verify(credentials);
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String password = payload.getSubject();
        if (!userExistsByEmail.execute(email))
            createPassenger.execute(constructPassengerRequest(payload), AccountProvider.GOOGLE);
        return logInUser.execute(email, password);*/
        return "";
    }
}
