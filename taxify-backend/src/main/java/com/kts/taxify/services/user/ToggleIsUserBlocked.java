package com.kts.taxify.services.user;

import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.request.user.ToggleIsBlockedRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToggleIsUserBlocked {
    private final GetUserByEmail getUserByEmail;
    private final SaveUser saveUser;

    public UserResponse execute(final ToggleIsBlockedRequest toggleIsBlockedRequest) {
        User user = getUserByEmail.execute(toggleIsBlockedRequest.getEmail());
        user.setBlocked(!user.isBlocked());
        return UserConverter.toUserResponse(saveUser.execute(user));
    }
}
