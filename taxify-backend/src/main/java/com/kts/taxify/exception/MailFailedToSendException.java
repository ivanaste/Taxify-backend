package com.kts.taxify.exception;

public class MailFailedToSendException extends CustomRuntimeException {
    public MailFailedToSendException() {
        super(ExceptionKeys.MAIL_FAILED);
    }
}
