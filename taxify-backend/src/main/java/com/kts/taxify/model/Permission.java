package com.kts.taxify.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    REGISTER_DRIVER,

    GET_ALL_NOTIFICATIONS,
    GET_DRIVER_INFO,

    SET_DRIVER_INACTIVE,

    ANSWER_ON_ADDING_TO_THE_RIDE,

    LINK_PASSENGERS_TO_THE_RIDE,

    FIND_SUITABLE_DRIVER,

    GET_ASSIGNED_RIDE,

    FINISH_RIDE,

    VEHICLE_ARRIVED,

    REJECT_RIDE,

    LEAVE_COMPLAINT;

    @Override
    public String getAuthority() {
        return name();
    }
}
