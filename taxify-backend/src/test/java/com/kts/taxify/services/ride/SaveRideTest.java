package com.kts.taxify.services.ride;

import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.RideRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveRideTest {

    @Mock
    private RideRepository rideRepository;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    @InjectMocks
    private SaveRide saveRide;

    @Test
    @DisplayName("Should save ride")
    public void shouldSaveRide() {
        Ride testRide = new Ride();
        testRide.setStatus(RideStatus.PENDING);

        when(rideRepository.save(testRide)).thenReturn(testRide);
        testRide = saveRide.execute(testRide);

        assertEquals(RideStatus.PENDING, testRide.getStatus());
        verify(rideRepository, times(1)).save(rideArgumentCaptor.capture());
    }
}
