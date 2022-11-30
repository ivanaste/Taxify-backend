package com.kts.taxify.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    REGISTER_DRIVER;
    //permission list

    @Override
    public String getAuthority() {
        return name();
    }
}
