package com.kts.taxify.dto.request.password;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    private String newPassword;
    private String newPasswordConfirmation;
    private String authToken;
}
