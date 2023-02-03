package com.kts.taxify.services.password;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.exception.AccountNotLocalException;
import com.kts.taxify.exception.PassengerAccountNotActivatedException;
import com.kts.taxify.model.*;
import com.kts.taxify.services.jwt.JwtGenerateToken;
import com.kts.taxify.services.mail.SendMail;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.translations.Codes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.kts.taxify.constants.LinkConstants.PASSWORD_RESET_PATH;
import static com.kts.taxify.translations.Translator.toLocale;

@Service
@RequiredArgsConstructor
public class SendPasswordResetEmail {
    private final SendMail sendMail;
    private final JwtGenerateToken jwtGenerateToken;
    private final CustomProperties customProperties;
    private final GetUserByEmail getUserByEmail;

    @Transactional(readOnly = true)
    public ResponseEntity<String> execute(final String email) {
        final User user = getUserByEmail.execute(email);
        if (!user.getAccountProvider().equals(AccountProvider.LOCAL)) throw new AccountNotLocalException();
        if (user instanceof Passenger) {
            if (!Objects.equals(((Passenger) user).getStatus(), PassengerStatus.ACTIVE)) throw new PassengerAccountNotActivatedException();
        }
        final String resetPasswordUrl = constructResetPasswordUrl(user);
        final EmailDetails emailDetails = new EmailDetails(email, toLocale(Codes.PASSWORD_RESET_LINK, new String[]{resetPasswordUrl}), toLocale(Codes.PASSWORD_RESET_EMAIL_SUBJECT));
        sendMail.execute(emailDetails);
        return new ResponseEntity<>(toLocale(Codes.PASSWORD_RESET_REQUEST_SUCCESS), HttpStatus.OK);
    }

    private String constructResetPasswordUrl(final User user) {
        final String authToken = jwtGenerateToken.execute(user.getEmail(), customProperties.getJwtForgotPasswordTokenExpiration());
        return customProperties.getClientUrl().concat(PASSWORD_RESET_PATH).concat(authToken);
    }
}
