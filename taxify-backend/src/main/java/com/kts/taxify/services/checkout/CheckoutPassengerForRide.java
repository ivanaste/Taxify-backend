package com.kts.taxify.services.checkout;

import com.kts.taxify.dto.response.PaymentResponse;
import com.kts.taxify.exception.RideNotFoundException;
import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutPassengerForRide {
    private final SaveCharge saveCharge;
    private final Checkout checkout;

    public PaymentResponse execute(Ride ride, Passenger passenger) throws StripeException {
        for (Charge charge : ride.getPassengersCharges()) {
            if (charge.getCustomerId().equals(passenger.getCustomerId())) {
                charge.setAmount(ride.getRoute().getPrice() / ride.getPassengers().size());
                PaymentResponse response = checkout.execute(charge);
                charge.setPaymentId(response.getId());
                saveCharge.execute(charge);
                return response;
            }
        }

        throw new RideNotFoundException();
    }
}
