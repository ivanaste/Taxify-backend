package com.kts.taxify.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<?> handleBadCredentialsException() {
		return buildResponseEntity(new ApiException("Bad credentials exception", HttpStatus.UNAUTHORIZED));
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	protected ResponseEntity<?> handleUserAlreadyExistsException() {
		return buildResponseEntity(new ApiException("User already exists", HttpStatus.CONFLICT));
	}

	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<?> handleUnauthorizedExceptions() {
		return buildResponseEntity(new ApiException("Sranje", HttpStatus.UNAUTHORIZED));
	}

	private ResponseEntity<?> buildResponseEntity(final ApiException err) {
		return new ResponseEntity<>(err, err.getStatus());
	}

	//	private ResponseEntity<?> buildResponseEntity(final CustomApiError err, final HttpStatus httpStatus) {
	//		return new ResponseEntity<>(err, httpStatus);
	//	}

}
