package com.kts.taxify.services.auth;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.exception.PassengerNotActiveException;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.PassengerStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.services.driver.MakeDriverActive;
import com.kts.taxify.services.driverTimetable.GetDriverRemainingWorkTime;
import com.kts.taxify.services.jwt.JwtGenerateToken;
import com.kts.taxify.services.user.GetUserByEmail;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogInUser {
	private final AuthenticationManager authenticationManager;

	private final JwtGenerateToken jwtGenerateToken;

	private final GetUserByEmail getUserByEmail;

	private final CustomProperties customProperties;

	private final MakeDriverActive makeDriverActive;

	private final GetDriverRemainingWorkTime getDriverRemainingWorkTime;

	public AuthTokenResponse execute(final String email, final String password) {
		final Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
			);
		} catch (final Exception e) {
			throw new BadCredentialsException("Bad login credentials");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		final User user = getUserByEmail.execute(userDetails.getUsername());

		if (user.getRole().getName().equals("PASSENGER") && ((Passenger) user).getStatus().equals(PassengerStatus.PENDING)) {
			throw new PassengerNotActiveException();
		}

		//da li je driver blokiran

		if (user.getRole().getName().equals("DRIVER") && getDriverRemainingWorkTime.execute(user.getEmail()) > 0) {
			makeDriverActive.execute(user.getEmail());
		}

		return new AuthTokenResponse(jwtGenerateToken.execute(user.getEmail(), customProperties.getAuthTokenExpirationMilliseconds()),
			customProperties.getAuthTokenExpirationMilliseconds(), user.getRole().getName());
	}
}
