package com.kts.taxify.services.ride;

import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.*;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.user.GetUserByEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAssignedRideTest {
    @Mock
    private GetUserByEmail getUserByEmail;

    @Mock
    private GetSelf getSelf;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private NotifyDriver notifyDriver;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    @InjectMocks
    private GetAssignedRide getAssignedRide;

    @Test
    @DisplayName("Should get driver assigned ride without notifying")
    public void shouldGetCurrentlyAssignedRide() {
        Driver driver = new Driver();
        Ride ride = new Ride();
        UUID id = UUID.randomUUID();
        ride.setId(id);
        ride.setStatus(RideStatus.ARRIVED);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("test@gmail.com");

        when(getSelf.execute()).thenReturn(userResponse);
        when(getUserByEmail.execute(userResponse.getEmail())).thenReturn(driver);
        when(rideRepository.findFirstByDriverAndStatusIn(driver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION))).thenReturn(ride);

        Ride actualRide = getAssignedRide.execute();

        assertEquals(id, actualRide.getId());
        verify(notifyDriver, never()).execute(anyString(), any(NotificationType.class));
        verify(rideRepository, never()).getRideByPassengersContainingAndStatusIn(any(Passenger.class), anyList());
    }

    @Test
    @DisplayName("Should get driver assigned ride with notifying")
    public void shouldGetCurrentlyAssignedRideWithNotifyingDriver() {
        Driver driver = Driver.builder().email("test@gmail.com").build();
        Ride ride = new Ride();
        UUID id = UUID.randomUUID();
        ride.setId(id);
        ride.setStatus(RideStatus.ON_DESTINATION);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("test@gmail.com");

        when(getSelf.execute()).thenReturn(userResponse);
        when(getUserByEmail.execute(userResponse.getEmail())).thenReturn(driver);
        when(rideRepository.findFirstByDriverAndStatusIn(driver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION))).thenReturn(ride);

        Ride actualRide = getAssignedRide.execute();

        assertEquals(id, actualRide.getId());
        verify(notifyDriver, times(1)).execute(driver.getEmail(), NotificationType.ON_DESTINATION_DRIVER);
        verify(rideRepository, never()).getRideByPassengersContainingAndStatusIn(any(Passenger.class), anyList());
    }

    @Test
    @DisplayName("Should get passenger assigned ride")
    public void shouldGetAssignedRideOfPassenger() {
        Passenger passenger = Passenger.builder().email("test@gmail.com").build();
        Ride ride = new Ride();
        UUID id = UUID.randomUUID();
        ride.setId(id);
        ride.setStatus(RideStatus.ON_DESTINATION);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("test@gmail.com");

        when(getSelf.execute()).thenReturn(userResponse);
        when(getUserByEmail.execute(userResponse.getEmail())).thenReturn(passenger);
        when(rideRepository.getRideByPassengersContainingAndStatusIn(passenger, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ARRIVED, RideStatus.STARTED, RideStatus.ON_DESTINATION))).thenReturn(ride);

        Ride actualRide = getAssignedRide.execute();

        assertEquals(id, actualRide.getId());
        verify(notifyDriver, never()).execute(anyString(), any(NotificationType.class));
    }
}
