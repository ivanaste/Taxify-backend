package com.kts.taxify.dto.request.passenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class GoogleSignupRequest {
    @NotEmpty
    private String credentials;
    @NotEmpty
    private String city;
    @NotEmpty
    private String phoneNumber;
}
