package com.kts.taxify.services.ride;

import com.kts.taxify.exception.RideNotFoundException;
import com.kts.taxify.model.Ride;
import com.kts.taxify.repository.RideRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetRideByIdTest {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private GetRideById getRideById;

    @Test
    @DisplayName("Should return ride by id")
    public void getByExistingId() {
        Ride ride = Ride.builder().sender("test@gmail.com").build();
        UUID rideId = UUID.randomUUID();
        ride.setId(rideId);

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        Ride actualRide = getRideById.execute(rideId);
        assertEquals(rideId, actualRide.getId());
        assertEquals(ride.getSender(), actualRide.getSender());
    }

    @Test
    @DisplayName("Should throw exception for ride not found")
    public void getByNonExistingId() {
        RideNotFoundException exception = assertThrows(RideNotFoundException.class, () -> getRideById.execute(UUID.randomUUID()));
        assertEquals("ride_not_found", exception.getKey().getCode());
    }

}
