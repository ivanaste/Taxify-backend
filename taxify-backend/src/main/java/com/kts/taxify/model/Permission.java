package com.kts.taxify.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    REGISTER_DRIVER,

    GET_ALL_NOTIFICATIONS;
    //permission list

    @Override
    public String getAuthority() {
        return name();
    }
}
