package com.kts.taxify.controller;

import com.kts.taxify.dto.request.password.ChangePasswordRequest;
import com.kts.taxify.services.password.ChangePassword;
import com.kts.taxify.services.password.SendPasswordResetEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final SendPasswordResetEmail sendPasswordResetEmail;
    private final ChangePassword changePassword;

    @GetMapping("/request-change")
    public String requestPasswordChange(@Email @RequestParam("email") String email) {
        return sendPasswordResetEmail.execute(email);
    }

    @PutMapping("/change")
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordDTO) {
        changePassword.execute(changePasswordDTO);
    }
}
