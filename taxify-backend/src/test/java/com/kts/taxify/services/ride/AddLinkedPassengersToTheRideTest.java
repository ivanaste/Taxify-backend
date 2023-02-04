package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.notification.AddLinkedPassengersToTheRide;
import com.kts.taxify.services.passenger.GetPassengersByEmails;
import com.kts.taxify.services.passenger.NotifyRecipientsOfAddingToTheRide;
import com.kts.taxify.services.user.GetUserByEmail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddLinkedPassengersToTheRideTest {

    @Mock
    private GetUserByEmail getUserByEmail;

    @Mock
    private GetPassengersByEmails getPassengersByEmails;

    @Mock
    private SaveRide saveRide;

    @Mock
    private NotifyRecipientsOfAddingToTheRide notifyRecipientsOfAddingToTheRide;

    @InjectMocks
    private AddLinkedPassengersToTheRide addLinkedPassengersToTheRide;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    private static LinkedPassengersToTheRideRequest linkedPassengers;

    @BeforeAll
    private static void createLinkedPassengersRequest() {
        linkedPassengers = LinkedPassengersToTheRideRequest.builder().senderEmail("test1@gmail.com").recipientsEmails(new HashSet<>(Arrays.asList("test2@gmail.com", "test3@gmail.com"))).build();
    }

    @Test
    @DisplayName("Should add all linked passengers to the ride")
    public void shouldAddLinkedPassengers() {
        Passenger sender = Passenger.builder().email("test1@gmail.com").build();
        Passenger recipient1 = Passenger.builder().email("test2@gmail.com").build();
        Passenger recipient2 = Passenger.builder().email("test3@gmail.com").build();

        when(getPassengersByEmails.execute(linkedPassengers.getRecipientsEmails())).thenReturn(new HashSet<>(Arrays.asList(recipient1, recipient2)));
        when(getUserByEmail.execute(linkedPassengers.getSenderEmail())).thenReturn(sender);

        addLinkedPassengersToTheRide.execute(linkedPassengers);

        verify(saveRide, times(1)).execute(rideArgumentCaptor.capture());
        assertThat(rideArgumentCaptor.getValue().getStatus()).isEqualTo(RideStatus.PENDING);
        assertThat(rideArgumentCaptor.getValue().getSender()).isEqualTo(sender.getEmail());
    }


}
