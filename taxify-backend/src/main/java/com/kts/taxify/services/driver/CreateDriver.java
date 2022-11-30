package com.kts.taxify.services.driver;

import com.kts.taxify.converter.UserConverter;
import com.kts.taxify.dto.request.driver.CreateDriverRequest;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.exception.UserAlreadyExistsException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.UserRole;
import com.kts.taxify.model.Vehicle;
import com.kts.taxify.services.user.SaveUser;
import com.kts.taxify.services.user.UserExistsByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class CreateDriver {
    private final UserExistsByEmail userExistsByEmail;

    private final PasswordEncoder passwordEncoder;

    private final SaveUser saveUser;

    public UserResponse execute(@Valid final CreateDriverRequest createDriverRequest) {
        if (userExistsByEmail.execute(createDriverRequest.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        final Vehicle vehicle = Vehicle.builder()
                .brand(createDriverRequest.getVehicleBrand())
                .model(createDriverRequest.getVehicleModel())
                .horsePower(createDriverRequest.getVehicleHorsePower())
                .type(createDriverRequest.getVehicleType())
                .babyFriendly(createDriverRequest.getBabyFriendly())
                .petFriendly(createDriverRequest.getPetFriendly())
                .build();

        final Driver driver = Driver.builder()
                .email(createDriverRequest.getEmail())
                .name(createDriverRequest.getName())
                .surname(createDriverRequest.getSurname())
                .passwordHash(passwordEncoder.encode(createDriverRequest.getPassword()))
                .city(createDriverRequest.getCity())
                .phoneNumber(createDriverRequest.getPhoneNumber())
                .profilePicture(createDriverRequest.getProfilePicture())
                .role(UserRole.DRIVER)
                .vehicle(vehicle)
                .build();

        return UserConverter.toUserResponse(saveUser.execute(driver));
    }
}
