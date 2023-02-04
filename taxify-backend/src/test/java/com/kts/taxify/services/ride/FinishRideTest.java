package com.kts.taxify.services.ride;

import com.kts.taxify.model.*;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinishRideTest {
    @Mock
    private GetOnDestinationRideDriver getOnDestinationRideDriver;

    @Mock
    private SaveRide saveRide;

    @Mock
    private NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    @Mock
    private NotifyDriver notifyDriver;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    @InjectMocks
    private FinishRide finishRide;

    @Test
    @DisplayName("Should finish ride")
    public void shouldFinishRide() {
        Vehicle vehicle = new Vehicle();
        Driver driver = Driver.builder().email("test@gmail.com").vehicle(vehicle).build();
        Ride testRide = Ride.builder().driver(driver).status(RideStatus.ACCEPTED).passengers(new HashSet<>(Arrays.asList(Passenger.builder().email("test@gmail.com").build(), Passenger.builder().email("test1@gmail.com").build(),Passenger.builder().email("test2@gmail.com").build()))).build();

        when(getOnDestinationRideDriver.execute()).thenReturn(testRide);
        when(saveRide.execute(testRide)).thenReturn(testRide);

        finishRide.execute();

        verify(saveRide, times(1)).execute(rideArgumentCaptor.capture());
        assertThat(rideArgumentCaptor.getValue().getStatus()).isEqualTo(RideStatus.FINISHED);
        assertThat(rideArgumentCaptor.getValue().getDriver().getVehicle().getOccupied()).isEqualTo(false);
        assertThat(rideArgumentCaptor.getValue().getFinishedAt()).isEqualToIgnoringSeconds(LocalDateTime.now());
        verify(saveRide, times(1)).execute(any(Ride.class));
        verify(notifyDriver, times(1)).execute("test@gmail.com", NotificationType.RIDE_FINISHED_DRIVER);
        verify(notifyPassengerOfChangedRideState, times(1)).execute("test@gmail.com", NotificationType.RIDE_FINISHED_PASSENGER);
        verify(notifyPassengerOfChangedRideState, times(1)).execute("test1@gmail.com", NotificationType.RIDE_FINISHED_PASSENGER);
        verify(notifyPassengerOfChangedRideState, times(1)).execute("test2@gmail.com", NotificationType.RIDE_FINISHED_PASSENGER);

    }
}
