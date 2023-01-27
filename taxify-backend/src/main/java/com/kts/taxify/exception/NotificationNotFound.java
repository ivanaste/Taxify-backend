package com.kts.taxify.exception;

public class NotificationNotFound extends CustomRuntimeException {
	public NotificationNotFound() {
		super(ExceptionKeys.NOTIFICATION_NOT_FOUND);
	}

}
