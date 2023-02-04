package com.kts.taxify.services.ride;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.model.*;
import com.kts.taxify.services.checkout.RefundChargedPassengersInRide;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.simulations.FindActiveProcess;
import com.kts.taxify.simulatorModel.ToClientData;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RejectRideTest {

    @Mock
    private SaveRide saveRide;
    @Mock
    private GetDriverAssignedRide getDriverAssignedRide;
    @Mock
    private FindActiveProcess findActiveProcess;

    @Mock
    private NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    @Mock
    private RefundChargedPassengersInRide refundChargedPassengersInRide;

    @InjectMocks
    private RejectRide rejectRide;
    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Process process;

    @BeforeEach
    private void createProcess() throws IOException {
        Location location = Location.builder().latitude(45.23814).longitude(19.83879).build();
        Location clientLocation = Location.builder().latitude(45.236058).longitude(19.841273).build();

        ToClientData toClientData = new ToClientData("08a26db0-21f1-4641-b74c-4ea70a536494", location, clientLocation);
        String dataStringMacOS = objectMapper.writeValueAsString(toClientData);
        process = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_to_client.py", "--conf", "vehicleMovementScripts/locust.conf", "--data",
                dataStringMacOS).start();
    }

    @Test
    @DisplayName("Should reject ride")
    public void shouldRejectRide() throws StripeException {
        Vehicle vehicle = new Vehicle();
        Driver driver = Driver.builder().vehicle(vehicle).build();
        Ride testRide = Ride.builder().driver(driver).status(RideStatus.ACCEPTED).passengers(new HashSet<>(Arrays.asList(Passenger.builder().email("test1@gmail.com").build(),Passenger.builder().email("test2@gmail.com").build()))).build();
        testRide.setId(UUID.randomUUID());

        when(getDriverAssignedRide.execute()).thenReturn(testRide);
        when(findActiveProcess.execute(testRide.getId())).thenReturn(process);
        when(saveRide.execute(testRide)).thenReturn(testRide);

        String rejectionReason = "vozac je kasnio";
        rejectRide.execute(rejectionReason);

        verify(saveRide, times(1)).execute(rideArgumentCaptor.capture());
        assertThat(rideArgumentCaptor.getValue().getStatus()).isEqualTo(RideStatus.REJECTED);
        assertEquals(RideStatus.REJECTED, testRide.getStatus());
        assertEquals(rejectionReason, testRide.getRejectionReason());
    }

}
