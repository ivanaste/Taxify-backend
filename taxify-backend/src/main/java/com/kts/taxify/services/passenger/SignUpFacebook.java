package com.kts.taxify.services.passenger;

import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.request.passenger.FacebookSignupRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.exception.UserAlreadyExistsException;
import com.kts.taxify.model.AccountProvider;
import com.kts.taxify.services.auth.LogInUser;
import com.kts.taxify.services.user.UserExistsByEmail;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpFacebook {
    private final LogInUser logInUser;
    private final UserExistsByEmail userExistsByEmail;
    private final CreatePassenger createPassenger;

    public AuthTokenResponse execute(final FacebookSignupRequest facebookSignUpRequest) throws StripeException {
        if (!userExistsByEmail.execute(facebookSignUpRequest.getEmail())) {
            CreatePassengerRequest passengerRequest = constructPassengerRequest(facebookSignUpRequest);
            createPassenger.execute(passengerRequest, AccountProvider.FACEBOOK);
            return logInUser.execute(passengerRequest.getEmail(), passengerRequest.getPassword());
        } else throw new UserAlreadyExistsException();
    }


    private CreatePassengerRequest constructPassengerRequest(FacebookSignupRequest facebookSignupRequest) {
        return CreatePassengerRequest.builder()
                .email(facebookSignupRequest.getEmail())
                .password(facebookSignupRequest.getId())
                .name(facebookSignupRequest.getFirstName())
                .surname(facebookSignupRequest.getLastName())
                .phoneNumber(facebookSignupRequest.getPhoneNumber())
                .city(facebookSignupRequest.getCity())
                .profilePicture(Strings.EMPTY)
                .build();
    }
}
