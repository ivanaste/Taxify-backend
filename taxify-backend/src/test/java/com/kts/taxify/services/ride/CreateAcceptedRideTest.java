package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.Route;
import com.kts.taxify.services.user.GetUserByEmail;
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
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("Should create accepted ride for one passenger")
    public void shouldCreateAcceptedRideForOnePassenger() throws IOException, InterruptedException {
        //ovu popravi
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        rideRequest.setPassengers(createLinkedPassengersRequest());
        rideRequest.setPaymentMethodId("1JHB");
        Passenger sender = Passenger.builder().customerId("dasd").build();
        Double price = 400.00;
        Ride ride = Ride.builder().passengers(createPassengers()).route(Route.builder().price(price).build()).build();
        Double splitFarePrice = price / ride.getPassengers().size();
        Driver assignedDriver = new Driver();

        when(getUserByEmail.execute(rideRequest.getPassengers().getSenderEmail())).thenReturn(sender);
        when(createAcceptedRideForMultiplePassengers.execute(rideRequest, assignedDriver, sender)).thenReturn(ride);
        when(addChargeToRide.execute(ride, sender.getCustomerId(), rideRequest.getPaymentMethodId())).thenReturn(ride);

        Ride actualRide = createAcceptedRide.execute(rideRequest, assignedDriver);
        assertEquals(ride.getPassengers(), actualRide.getPassengers());
    }

    private LinkedPassengersToTheRideRequest createLinkedPassengersRequest() {
        Set<String> recipientsEmails = new HashSet<>();
        recipientsEmails.add("test1@gmail.com");
        recipientsEmails.add("test2@gmail.com");
        return LinkedPassengersToTheRideRequest.builder().senderEmail("test3@gmail.com").recipientsEmails(recipientsEmails).build();
    }

    private Set<Passenger> createPassengers() {
        Set<Passenger> passengers = new HashSet<>();
        passengers.add(Passenger.builder().build());
        passengers.add(Passenger.builder().build());
        return passengers;
    }
}
