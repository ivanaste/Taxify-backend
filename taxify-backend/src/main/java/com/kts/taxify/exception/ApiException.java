package com.kts.taxify.exception;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException {
	@JsonIgnore
	private final HttpStatus status;

	private final String message;

	public ApiException(final String message, final HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	@JsonGetter
	public int getStatusCode() {
		return status.value();
	}

	@JsonGetter
	public String getStatusMessage() {
		return status.getReasonPhrase();
	}
}
