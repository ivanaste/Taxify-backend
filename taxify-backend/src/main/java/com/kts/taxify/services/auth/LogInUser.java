package com.kts.taxify.services.auth;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.exception.PassengerNotActiveException;
import com.kts.taxify.exception.UserBlockedException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.PassengerStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.model.UserRole;
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
	
	public String execute(final String email, final String password) {
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

		if (user.getRole() == UserRole.PASSENGER) {
			if (((Passenger) user).getStatus() == PassengerStatus.PENDING) {
				throw new PassengerNotActiveException();
			}
			if (((Passenger) user).getStatus() == PassengerStatus.BLOCKED) {
				throw new UserBlockedException();
			}
		}

		if (user.getRole() == UserRole.DRIVER) {
			if (((Driver) user).isBlocked()) {
				throw new UserBlockedException();
			}
		}
		return jwtGenerateToken.execute(user.getEmail(), customProperties.getAuthTokenExpirationMilliseconds());
	}
}
