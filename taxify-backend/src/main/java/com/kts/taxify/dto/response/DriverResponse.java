package com.kts.taxify.dto.response;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    @NotEmpty
    UUID id;

    @NotEmpty
    String name;

    @NotEmpty
    String surname;

    @NotEmpty
    String phoneNumber;

    @Email
    @NotEmpty
    String email;

    @NotEmpty
    Boolean active;

    String profilePicture;

    @NotEmpty
    VehicleResponse vehicle;
}
