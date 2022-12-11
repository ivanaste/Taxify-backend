package com.kts.taxify.dto.request.passenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class FacebookSignupRequest {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String city;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String id;
}
