package com.kts.taxify.services.passenger;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.exception.UserAlreadyExistsException;
import com.kts.taxify.model.AccountProvider;
import com.kts.taxify.model.EmailDetails;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.PassengerStatus;
import com.kts.taxify.services.checkout.CreateStripeCustomer;
import com.kts.taxify.services.jwt.JwtGenerateToken;
import com.kts.taxify.services.mail.SendMail;
import com.kts.taxify.services.role.GetRoleByName;
import com.kts.taxify.services.user.SaveUser;
import com.kts.taxify.services.user.UserExistsByEmail;
import com.kts.taxify.translations.Codes;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Objects;

import static com.kts.taxify.constants.LinkConstants.EMAIL_ACTIVATION_PATH;
import static com.kts.taxify.translations.Translator.toLocale;

@Service
@RequiredArgsConstructor
public class CreatePassenger {

    private final UserExistsByEmail userExistsByEmail;

    private final PasswordEncoder passwordEncoder;

    private final SaveUser saveUser;
    private final GetRoleByName getRoleByName;

    private final SendMail sendMail;

    private final JwtGenerateToken jwtGenerateToken;

    private final CustomProperties customProperties;

    private final CreateStripeCustomer createStripeCustomer;

    public UserResponse execute(@Valid final CreatePassengerRequest createPassengerRequest, AccountProvider accountProvider) throws StripeException {
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
                .accountProvider(accountProvider)
                .status(Objects.equals(accountProvider, AccountProvider.LOCAL) ? PassengerStatus.PENDING : PassengerStatus.ACTIVE)
                .build();
        if (Objects.equals(accountProvider, AccountProvider.LOCAL)) {
            final String activateEmailUrl = constructActivateEmailUrl(passenger.getEmail());
            final EmailDetails emailDetails = new EmailDetails(passenger.getEmail(), toLocale(Codes.PASSENGER_SIGN_UP_ACTIVATION_EMAIL, new String[]{activateEmailUrl}),
                    toLocale(Codes.PASSENGER_SIGN_UP_ACTIVATION_EMAIL_SUBJECT));
            sendMail.execute(emailDetails);
        }

        Customer customer = createStripeCustomer.execute(passenger);
        passenger.setCustomerId(customer.getId());
        return UserConverter.toUserResponse(saveUser.execute(passenger));
    }

    private String constructActivateEmailUrl(final String passengerEmail) {
        final String authToken = jwtGenerateToken.execute(passengerEmail, customProperties.getJwtActivateEmailTokenExpiration());
        return customProperties.getClientUrl().concat(EMAIL_ACTIVATION_PATH).concat(authToken);
    }

}
