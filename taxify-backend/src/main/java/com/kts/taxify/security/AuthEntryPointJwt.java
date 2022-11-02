package com.kts.taxify.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	@Qualifier("handlerExceptionResolver")
	private final HandlerExceptionResolver exceptionResolver;

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException)
		throws IOException, ServletException {
		exceptionResolver.resolveException(request, response, null, authException);
	}
}
