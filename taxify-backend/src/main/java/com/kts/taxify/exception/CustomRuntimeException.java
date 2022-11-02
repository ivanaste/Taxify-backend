package com.kts.taxify.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException {
	private ExceptionKeys keys;
}
