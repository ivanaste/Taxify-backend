package com.kts.taxify.exception;

import com.stripe.exception.StripeException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kts.taxify.translations.Translator.toLocale;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            PasswordSameException.class,
            PasswordMismatchException.class})
    protected ResponseEntity<?> handleBadRequestExceptions(CustomRuntimeException ex) {
        return buildResponseEntity(new ApiException(toLocale(ex.getKey()), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({
            AuthTokenExpiredException.class,
            AuthTokenInvalidException.class,
            UnauthorizedException.class,
    })
    protected ResponseEntity<?> handleUnauthorizedExceptions(CustomRuntimeException ex) {
        return buildResponseEntity(new ApiException(toLocale(ex.getKey()), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler({AccessDeniedException.class, PassengerNotActiveException.class})
    protected ResponseEntity<?> handleAccessDeniedException() {
        return buildResponseEntity(new ApiException(toLocale(ExceptionKeys.INSUFFICIENT_PERMISSIONS), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler({GeneralSecurityException.class, InvalidGoogleAccountException.class})
    protected ResponseEntity<?> handleInvalidSignInExceptions() {
        return buildResponseEntity(new ApiException(toLocale(ExceptionKeys.INVALID_GOOGLE_ACCOUNT), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler({
            RoleNotFoundException.class
    })
    protected ResponseEntity<?> handleNotFoundExceptions(CustomRuntimeException ex) {
        return buildResponseEntity(new ApiException(toLocale(ex.getKey()), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> handleBadCredentialsException() {
        return buildResponseEntity(new ApiException(toLocale(ExceptionKeys.BAD_LOGIN_CREDENTIALS), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    protected ResponseEntity<?> handleInsufficientAuthenticationException() {
        return buildResponseEntity(new ApiException(toLocale(ExceptionKeys.MISSING_AUTHENTICATION), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultipartException(MultipartException e) {
        return buildResponseEntity(new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<?> handeStripeException(StripeException e) {
        return buildResponseEntity(new ApiException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode())));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>(ex.getBindingResult().getFieldErrors().stream().map((err) ->
                err.getField() + " " + err.getDefaultMessage()
        ).toList());

        errors.addAll(ex.getBindingResult().getGlobalErrors().stream().map((err) ->
                err.getObjectName() + " " + err.getDefaultMessage()
        ).toList());

        String errorMessage = String.join(", ", errors);

        return (ResponseEntity<Object>) buildResponseEntity(new ApiException(errorMessage, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<?> defaultExceptionHandler(Throwable t) {
        logger.error("Unhandled exception: " + Strings.join(Arrays.asList(t.getStackTrace()), '\n'));
        return buildResponseEntity(new ApiException("Unhandled exception", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<?> buildResponseEntity(final ApiException err) {
        return new ResponseEntity<>(err, err.getStatus());
    }

}
