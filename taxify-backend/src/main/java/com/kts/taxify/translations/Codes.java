package com.kts.taxify.translations;

public enum Codes implements Translation {
    PASSWORD_RESET_LINK("password_reset_link"),
    PASSWORD_RESET_EMAIL_SUBJECT("password_reset_email_subject"),
    PASSWORD_RESET_REQUEST_SUCCESS("password_reset_request_success"),

    PASSENGER_SIGN_UP_ACTIVATION_EMAIL("passenger_sign_up_activation_email"),

    PASSENGER_SIGN_UP_ACTIVATION_EMAIL_SUBJECT("passenger_sign_up_activation_email_subject"),

    PASSENGER_EMAIL_ACTIVATION_SUCCESS("passenger_email_activation_success");

    private final String code;

    Codes(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
