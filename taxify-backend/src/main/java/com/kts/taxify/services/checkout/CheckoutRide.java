package com.kts.taxify.services.checkout;

import com.kts.taxify.dto.response.PaymentResponse;
import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.passenger.GetPassengerByCustomerId;
import com.kts.taxify.services.passenger.NotifyPassengerOfPaymentResultForRide;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutRide {
    private final RefundChargedPassengersInRide refundChargedPassengersInRide;
    private final SaveCharge saveCharge;
    private final GetPassengerByCustomerId getPassengerByCustomerId;
    private final CheckoutPassengerForRide checkoutPassengerForRide;
    private final NotifyPassengerOfPaymentResultForRide notifyPassengerOfPaymentResultForRide;

    public Boolean execute(Ride ride) throws StripeException {
        Boolean paymentDone = true;
        for (Charge charge : ride.getPassengersCharges()) {
            Passenger passenger = getPassengerByCustomerId.execute(charge.getCustomerId());
            String message;
            try {
                PaymentResponse paymentResponse = checkoutPassengerForRide.execute(ride, passenger, charge);
                charge.setCharged(true);
                charge.setPaymentId(paymentResponse.getId());
                saveCharge.execute(charge);
                message = "You have been successfully charged " + charge.getAmount() + " RSD.";
            } catch (StripeException e) {
                message = e.getUserMessage();
                paymentDone = false;
            }
            notifyPassengerOfPaymentResultForRide.execute(passenger.getEmail(), message);
        }
        if (!paymentDone) {
            refundChargedPassengersInRide.execute(ride);
        }
        return paymentDone;
    }
}
