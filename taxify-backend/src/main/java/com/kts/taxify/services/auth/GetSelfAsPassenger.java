package com.kts.taxify.services.auth;

import com.kts.taxify.exception.UnauthorizedException;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.GetUserById;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetSelfAsPassenger {
    private final GetUserById getUserById;

    public Passenger execute() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        // Getting the user from the DB, because the user from the auth principal
        // is not a managed object, so some lazy collections (assigned items) fail
        // to be fetched.
        final UUID userId = ((User) authentication.getPrincipal()).getId();
        return (Passenger) getUserById.execute(userId);
    }

}
