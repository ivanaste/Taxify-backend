package com.kts.taxify.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    REGISTER_DRIVER,

    PAYMENT_CHECKOUT,
    GET_PAYMENT_METHODS,
    SET_PAYMENT_METHODS,

    GET_DRIVER_INFO,

    SET_DRIVER_INACTIVE;
    //permission list

    @Override
    public String getAuthority() {
        return name();
    }
}
