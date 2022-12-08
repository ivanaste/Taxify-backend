package com.kts.taxify.services.passenger;

import static com.kts.taxify.constants.LinkConstants.EMAIL_ACTIVATION_PATH;
import static com.kts.taxify.translations.Translator.toLocale;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.exception.UserAlreadyExistsException;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.PassengerStatus;
import com.kts.taxify.services.user.SaveUser;
import com.kts.taxify.services.user.UserExistsByEmail;

import javax.validation.Valid;

import com.kts.taxify.services.jwt.JwtGenerateToken;
import com.kts.taxify.translations.Codes;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePassenger {

	private final UserExistsByEmail userExistsByEmail;

	private final PasswordEncoder passwordEncoder;

	private final SaveUser saveUser;

    private final SendMail sendMail;

    private final JwtGenerateToken jwtGenerateToken;

    private final CustomProperties customProperties;


    public UserResponse execute(@Valid final CreatePassengerRequest createPassengerRequest) {
        if (userExistsByEmail.execute(createPassengerRequest.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        final Passenger passenger = Passenger.builder()
            .email(createPassengerRequest.getEmail())
            .name(createPassengerRequest.getName())
            .surname(createPassengerRequest.getSurname())
            .passwordHash(passwordEncoder.encode(createPassengerRequest.getPassword()))
            .city(createPassengerRequest.getCity())
            .phoneNumber(createPassengerRequest.getPhoneNumber())
            .profilePicture(createPassengerRequest.getProfilePicture())
            .role(getRoleByName.execute("PASSENGER"))
            .status(PassengerStatus.PENDING)
            .build();

        //send activation email
        final String activateEmailUrl = constructActivateEmailUrl(passenger.getEmail());
        final EmailDetails emailDetails = new EmailDetails(passenger.getEmail(), toLocale(Codes.PASSENGER_SIGN_UP_ACTIVATION_EMAIL, new String[]{activateEmailUrl}),
            toLocale(Codes.PASSENGER_SIGN_UP_ACTIVATION_EMAIL_SUBJECT));
        sendMail.execute(emailDetails);

        return UserConverter.toUserResponse(saveUser.execute(passenger));
    }

    private String constructActivateEmailUrl(final String passengerEmail) {
        final String authToken = jwtGenerateToken.execute(passengerEmail, customProperties.getJwtActivateEmailTokenExpiration());
        return customProperties.getClientUrl().concat(EMAIL_ACTIVATION_PATH).concat(authToken);
    }

>>>>>>> Stashed changes
}
