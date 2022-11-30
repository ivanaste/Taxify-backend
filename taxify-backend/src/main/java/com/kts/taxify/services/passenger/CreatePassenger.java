package com.kts.taxify.services.passenger;

import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.request.passenger.CreatePassengerRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.exception.UserAlreadyExistsException;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.PassengerStatus;
import com.kts.taxify.services.role.GetRoleByName;
import com.kts.taxify.services.user.SaveUser;
import com.kts.taxify.services.user.UserExistsByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class CreatePassenger {

    private final UserExistsByEmail userExistsByEmail;

    private final PasswordEncoder passwordEncoder;

    private final SaveUser saveUser;
    private final GetRoleByName getRoleByName;

    public UserResponse execute(@Valid final CreatePassengerRequest createPassengerRequest) {
        if (userExistsByEmail.execute(createPassengerRequest.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        final Passenger passenger = Passenger.builder()
                .email(createPassengerRequest.getEmail())
                .name(createPassengerRequest.getName())
                .surname(createPassengerRequest.getSurname())
                .passwordHash(passwordEncoder.encode(createPassengerRequest.getPassword()))
                .city(createPassengerRequest.getCity())
                .phoneNumber(createPassengerRequest.getPhoneNumber())
                .profilePicture(createPassengerRequest.getProfilePicture())
                .role(getRoleByName.execute("PASSENGER"))
                .status(PassengerStatus.PENDING)
                .build();

        //send activation email

        return UserConverter.toUserResponse(saveUser.execute(passenger));
    }
}
