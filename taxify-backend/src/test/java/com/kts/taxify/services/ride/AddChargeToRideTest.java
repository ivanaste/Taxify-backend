package com.kts.taxify.services.ride;

import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.checkout.SaveCharge;
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
public class AddChargeToRideTest {
    @Mock
    private SaveCharge saveCharge;

    @Mock
    private SaveRide saveRide;

    @InjectMocks
    private AddChargeToRide addChargeToRide;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    @Test
    @DisplayName("Should add charge to the ride")
    public void shouldAddChargeToTheRide() {
        Ride ride = Ride.builder().build();

        addChargeToRide.execute(ride, "customer-id", "payment-id");

        verify(saveCharge, times(1)).execute(any(Charge.class));
        verify(saveRide, times(1)).execute(rideArgumentCaptor.capture());
        assertEquals(ride.getPassengersCharges().size(), rideArgumentCaptor.getValue().getPassengersCharges().size());
    }
}
