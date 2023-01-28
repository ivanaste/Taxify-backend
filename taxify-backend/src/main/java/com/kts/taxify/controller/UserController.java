package com.kts.taxify.controller;

import com.kts.taxify.dto.request.user.ToggleIsBlockedRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.user.GetAllUsers;
import com.kts.taxify.services.user.ToggleIsUserBlocked;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final GetAllUsers getAllUsers;
    private final ToggleIsUserBlocked toggleIsUserBlocked;

    @GetMapping("/all")
    @HasAnyPermission({Permission.GET_ALL_USERS})
    public Collection<UserResponse> getAllUsers() {
        return getAllUsers.execute();
    }

    @PutMapping("/toggle-blocked")
    @HasAnyPermission({Permission.BLOCK_USER})
    public UserResponse changeIsUserBlocked(@RequestBody final ToggleIsBlockedRequest toggleIsBlockedRequest) {
        return toggleIsUserBlocked.execute(toggleIsBlockedRequest);
    }
}
