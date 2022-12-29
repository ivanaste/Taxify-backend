package com.kts.taxify.services.passenger;

import static com.kts.taxify.translations.Translator.toLocale;

import com.kts.taxify.exception.UserNotFoundException;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.PassengerStatus;
import com.kts.taxify.services.jwt.JwtValidateAndGetUsername;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.services.user.SaveUser;
import com.kts.taxify.services.user.UserExistsByEmail;
import com.kts.taxify.translations.Codes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivateEmail {
	private final JwtValidateAndGetUsername jwtValidateAndGetUsername;

	private final GetUserByEmail getUserByEmail;

	private final UserExistsByEmail userExistsByEmail;

	private final SaveUser saveUser;

	public String execute(String token) {
		final String email = jwtValidateAndGetUsername.execute(token);
		if (!userExistsByEmail.execute(email)) {
			throw new UserNotFoundException();
		}
		final Passenger passenger = (Passenger) getUserByEmail.execute(email);
		passenger.setStatus(PassengerStatus.ACTIVE);
		saveUser.execute(passenger);
		return toLocale(Codes.PASSENGER_EMAIL_ACTIVATION_SUCCESS);
	}
}
