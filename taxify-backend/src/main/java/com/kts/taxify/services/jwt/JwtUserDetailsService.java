package com.kts.taxify.services.jwt;

import com.kts.taxify.model.User;
import com.kts.taxify.services.user.GetUserByEmail;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
	private final GetUserByEmail getUserByEmail;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = getUserByEmail.execute(username);

		return new org.springframework.security.core.userdetails.User(username, user.getPasswordHash(),
			Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}

