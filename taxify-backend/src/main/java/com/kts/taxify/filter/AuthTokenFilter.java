package com.kts.taxify.filter;

import com.kts.taxify.model.User;
import com.kts.taxify.services.jwt.JwtValidateAndGetUsername;
import com.kts.taxify.services.user.GetUserByEmail;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
	private final JwtValidateAndGetUsername jwtValidateAndGetUsername;
	private final GetUserByEmail getUserByEmail;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws
		ServletException, IOException {
		final String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String token = header.substring("Bearer ".length());
		final String username = jwtValidateAndGetUsername.execute(token);

		if (username == null) {
			filterChain.doFilter(request, response);
			return;
		}

		final User user = getUserByEmail.execute(username);
		// TODO: Roles/permission system
		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			user, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		filterChain.doFilter(request, response);
	}
}
