package com.kts.taxify.controller;

import com.kts.taxify.dto.request.auth.LoginRequest;
import com.kts.taxify.dto.request.auth.SelfChangeRequest;
import com.kts.taxify.dto.request.checkout.AddPaymentMethodRequest;
import com.kts.taxify.dto.response.AuthTokenResponse;
import com.kts.taxify.dto.response.PaymentMethodResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.auth.*;
import com.kts.taxify.services.user.UserExistsByEmail;
import com.kts.taxify.services.user.UserSignedWithGoogleExists;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LogInUser loginUser;
    private final GetSelf getSelf;
    private final ChangeSelf changeSelf;
    private final ChangeSelfProfilePicture changeSelfProfilePicture;
    private final ChangeSelfPassword changeSelfPassword;
    private final GetSelfPaymentMethods getSelfPaymentMethods;
    private final AddSelfPaymentMethod addSelfPaymentMethod;
    private final RemoveSelfPaymentMethod removeSelfPaymentMethod;
    private final SignInGoogle signInWithGoogle;
    private final UserExistsByEmail userExistsByEmail;
    private final UserSignedWithGoogleExists userSignedWithGoogleExists;
    private final IsOldPasswordCorrect isOldPasswordCorrect;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/self")
    public UserResponse getSelf() {
        return getSelf.execute();
    }

    @PutMapping("/self")
    public UserResponse changeSelf(@Valid @RequestBody final SelfChangeRequest selfChangeRequest) {
        return changeSelf.execute(selfChangeRequest.getName(), selfChangeRequest.getSurname(), selfChangeRequest.getPhoneNumber(), selfChangeRequest.getCity());
    }

    @PutMapping("/self-picture")
    public UserResponse changeSelfProfilePicture(@Valid @RequestParam final String profilePicture) {
        return changeSelfProfilePicture.execute(profilePicture);
    }

    @PutMapping("/self-password")
    public UserResponse changeSelfPassword(@Valid @RequestParam final String newPassword) {
        return changeSelfPassword.execute(newPassword);
    }

    @GetMapping("/self-paymentMethods")
    @HasAnyPermission({Permission.GET_PAYMENT_METHODS})
    public Collection<PaymentMethodResponse> getSelfPaymentMethods() throws StripeException {
        return getSelfPaymentMethods.execute();
    }

    @PostMapping("/self-paymentMethods")
    @HasAnyPermission({Permission.SET_PAYMENT_METHODS})
    public PaymentMethodResponse addSelfPaymentMethod(@RequestBody final AddPaymentMethodRequest addPaymentMethodRequest) throws StripeException {
        return addSelfPaymentMethod.execute(addPaymentMethodRequest);
    }

    @DeleteMapping("/self-paymentMethods")
    @HasAnyPermission({Permission.SET_PAYMENT_METHODS})
    public PaymentMethodResponse removeSelfPaymentMethod(@RequestParam final String paymentMethodId) throws StripeException {
        return removeSelfPaymentMethod.execute(paymentMethodId);
    }

    @PostMapping("/login-google/{credentials}")
    public AuthTokenResponse loginGoogle(@PathVariable("credentials") String credentials) throws GeneralSecurityException, IOException {
        return signInWithGoogle.execute(credentials);
    }

    @GetMapping("/user-exists/{email}")
    public Boolean userExists(@PathVariable("email") String email) {
        return userExistsByEmail.execute(email);
    }

    @GetMapping("/user-signed-with-google-exists/{credentials}")
    public Boolean userSignedWithGoogleExists(@PathVariable("credentials") String credentials) throws GeneralSecurityException, IOException {
        return userSignedWithGoogleExists.execute(credentials);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthTokenResponse login(@Valid @RequestBody final LoginRequest loginRequest) {
        return loginUser.execute(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/password-confirm")
    public boolean login(@RequestParam final String oldPassword) {
        return isOldPasswordCorrect.execute(oldPassword);
    }

}
