package com.kts.taxify.services.user;

import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.User;
import com.kts.taxify.repository.UserRepository;
import com.kts.taxify.services.auth.GetSelf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsers {
    private final UserRepository userRepository;
    private final GetSelf getSelf;

    public Collection<UserResponse> execute() {
        final UserResponse self = getSelf.execute();
        List<UserResponse> userResponses = new ArrayList<>();
        Collection<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.getId().equals(self.getId())) {
                userResponses.add(UserConverter.toUserResponse(user));
            }
        }
        return userResponses;
    }
}
