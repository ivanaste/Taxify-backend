package com.kts.taxify.services.checkout;

import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Ride;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundChargedPassengersInRide {
    private final Refund refund;

    public void execute(Ride ride) throws StripeException {
        for (Charge charge : ride.getPassengersCharges()) {
            if (charge.getCharged()) {
                refund.execute(charge.getAmount(), charge.getCustomerId(), charge.getPaymentId());
            }
        }
    }
}
