package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleAuthTokenResponse {
    String token;
    long expiresIn;
    String role;
    String email;

    public GoogleAuthTokenResponse(AuthTokenResponse authTokenResponse, String email) {
        this.token = authTokenResponse.getToken();
        this.expiresIn = authTokenResponse.expiresIn;
        this.role = authTokenResponse.getRole();
        this.email = email;
    }
}
