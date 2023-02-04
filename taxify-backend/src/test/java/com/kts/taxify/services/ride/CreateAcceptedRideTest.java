package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.Route;
import com.kts.taxify.services.user.GetUserByEmail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAcceptedRideTest {
    @Mock
    private CreateAcceptedRideForMultiplePassengers createAcceptedRideForMultiplePassengers;

    @Mock
    private CreateAcceptedRideForOnePassenger createAcceptedRideForOnePassenger;

    @Mock
    private GetUserByEmail getUserByEmail;

    @Mock
    private AddChargeToRide addChargeToRide;

    @InjectMocks
    private CreateAcceptedRide createAcceptedRide;

    private static Passenger passenger1;

    @BeforeAll
    private static void createPassenger() {
        passenger1 = Passenger.builder().customerId("customer-id").build();
    }

    @Test
    @DisplayName("Should create accepted ride for multiple passengers")
    public void shouldCreateAcceptedRideForMultiplePassengers() throws IOException, InterruptedException {
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        rideRequest.setPassengers(createLinkedPassengersRequest());
        rideRequest.setPaymentMethodId("payment-id");
        Ride ride = Ride.builder().route(Route.builder().price(400.00).build()).build();
        Driver assignedDriver = new Driver();

        when(getUserByEmail.execute(rideRequest.getPassengers().getSenderEmail())).thenReturn(passenger1);
        when(createAcceptedRideForMultiplePassengers.execute(rideRequest, assignedDriver, passenger1)).thenReturn(ride);
        when(addChargeToRide.execute(ride, passenger1.getCustomerId(), rideRequest.getPaymentMethodId())).thenReturn(ride);

        Ride actualRide = createAcceptedRide.execute(rideRequest, assignedDriver);
        assertEquals(ride.getPassengers(), actualRide.getPassengers());
        verify(createAcceptedRideForOnePassenger, never()).execute(any(RequestedRideRequest.class), any(Driver.class), any(Passenger.class));
    }

    @Test
    @DisplayName("Should create accepted ride for one passenger")
    public void shouldCreateAcceptedRideForOnePassenger() throws IOException, InterruptedException {
        RequestedRideRequest rideRequest = createRequestRideRequest();
        rideRequest.getPassengers().setRecipientsEmails(new HashSet<>());
        Passenger sender = Passenger.builder().customerId("customer-id").build();
        Ride ride = Ride.builder().route(Route.builder().price(400.00).build()).build();
        Driver assignedDriver = new Driver();

        when(getUserByEmail.execute(rideRequest.getPassengers().getSenderEmail())).thenReturn(sender);
        when(createAcceptedRideForOnePassenger.execute(rideRequest, assignedDriver, sender)).thenReturn(ride);
        when(addChargeToRide.execute(ride, sender.getCustomerId(), rideRequest.getPaymentMethodId())).thenReturn(ride);

        Ride actualRide = createAcceptedRide.execute(rideRequest, assignedDriver);
        assertEquals(ride.getPassengers(), actualRide.getPassengers());
        verify(createAcceptedRideForMultiplePassengers, never()).execute(any(RequestedRideRequest.class), any(Driver.class), any(Passenger.class));
    }

    private LinkedPassengersToTheRideRequest createLinkedPassengersRequest() {
        Set<String> recipientsEmails = new HashSet<>();
        recipientsEmails.add("test1@gmail.com");
        recipientsEmails.add("test2@gmail.com");
        return LinkedPassengersToTheRideRequest.builder().senderEmail("test3@gmail.com").recipientsEmails(recipientsEmails).build();
    }

    private RequestedRideRequest createRequestRideRequest() {
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        rideRequest.setPassengers(createLinkedPassengersRequest());
        rideRequest.setPaymentMethodId("payment-id");
        return rideRequest;
    }
}
