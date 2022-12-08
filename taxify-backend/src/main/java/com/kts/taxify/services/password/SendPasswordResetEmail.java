package com.kts.taxify.services.password;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.model.EmailDetails;
import com.kts.taxify.model.User;
import com.kts.taxify.services.jwt.JwtGenerateToken;
import com.kts.taxify.services.mail.SendMail;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.translations.Codes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String execute(final String email) {
        final User user = getUserByEmail.execute(email);
        //if (!Objects.equals(user.get, UserStatus.ACTIVE)) throw new UserNotActiveException();
        final String resetPasswordUrl = constructResetPasswordUrl(user);
        final EmailDetails emailDetails = new EmailDetails(email, toLocale(Codes.PASSWORD_RESET_LINK, new String[]{resetPasswordUrl}), toLocale(Codes.PASSWORD_RESET_EMAIL_SUBJECT));
        sendMail.execute(emailDetails);
        return toLocale(Codes.PASSWORD_RESET_REQUEST_SUCCESS);
    }

    private String constructResetPasswordUrl(final User user) {
        final String authToken = jwtGenerateToken.execute(user.getEmail(), customProperties.getJwtForgotPasswordExpiration());
        return customProperties.getClientUrl().concat(PASSWORD_RESET_PATH).concat(authToken);
    }
}
