package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

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

    String profilePicture;

    @NotEmpty
    VehicleResponse vehicle;
}
