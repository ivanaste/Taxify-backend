package com.kts.taxify.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class SelfChangeRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String city;
}
