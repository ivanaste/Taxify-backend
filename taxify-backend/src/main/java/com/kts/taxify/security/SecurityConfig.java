package com.kts.taxify.security;

import com.kts.taxify.exception.FilterChainExceptionHandler;
import com.kts.taxify.filter.AuthTokenFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthTokenFilter authTokenFilter;
	private final AuthEntryPointJwt authEntryPointJwt;

	private final FilterChainExceptionHandler filterChainExceptionHandler;

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()

			.antMatchers("/parking/closest").permitAll()
			.antMatchers("/vehicle/location").permitAll()
			.antMatchers("/vehicle/vehicleTypes").permitAll()
			.antMatchers("/simulation/**").permitAll()

			.antMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
			.antMatchers("/auth/login", "/auth/self", "/auth/login-google/{credentials}",
				"/auth/user-exists/{email}", "/auth/user-signed-with-google-exists/{credentials}")
			.permitAll()
			.antMatchers("/password/request-change", "/password/change").permitAll()
			.antMatchers("/passenger/create", "/passenger/google-signup", "/passenger/facebook-signup",
				"/passenger/activateEmail/{token}")
			.permitAll()

			.antMatchers("/ws/**").permitAll()
			.antMatchers("/driver/allActiveInArea").permitAll()

			//popravi
			.antMatchers("/notification/*").permitAll()
			.antMatchers("driver/workedTime/*").permitAll()
			.antMatchers("driver/changeActiveStatus/*").permitAll()

			.antMatchers("/**").authenticated()
			.anyRequest().authenticated();

		httpSecurity.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class);

		httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");

		corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return corsConfigurationSource;
	}

	@Bean
	public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration)
		throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
