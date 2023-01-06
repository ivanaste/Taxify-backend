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
public class UserResponse {
    @NotEmpty
    UUID id;

    @Email
    @NotEmpty
    String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    String city;

    @NotEmpty
    String phoneNumber;

    @NotEmpty
    String profilePicture;
}
