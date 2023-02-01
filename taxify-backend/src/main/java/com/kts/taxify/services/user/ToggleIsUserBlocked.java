package com.kts.taxify.services.user;

import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.request.user.ToggleIsBlockedRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.notification.CreateNotificationForUserIsBlockedChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToggleIsUserBlocked {
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final CreateNotificationForUserIsBlockedChanged createNotificationForUserIsBlockedChanged;
    private final SaveUser saveUser;

    public UserResponse execute(final ToggleIsBlockedRequest toggleIsBlockedRequest) {
        User user = getUserByEmail.execute(toggleIsBlockedRequest.getEmail());
        User self = getUserByEmail.execute(getSelf.execute().getEmail());
        user.setBlocked(!user.isBlocked());
        UserResponse response = UserConverter.toUserResponse(saveUser.execute(user));
        createNotificationForUserIsBlockedChanged.execute(self, user, toggleIsBlockedRequest.getReason());
        return response;
    }
}
