package com.kts.taxify.services.ride;

import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.checkout.SaveCharge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddChargeToRide {
    private final SaveCharge saveCharge;
    private final SaveRide saveRide;

    public Ride execute(Ride ride, String customerId, String paymentMethodId, Double amount) {
        Charge charge = Charge.builder()
                .ride(ride)
                .customerId(customerId)
                .amount(amount)
                .paymentId(paymentMethodId)
                .build();
        charge = saveCharge.execute(charge);
        ride.getPassengersCharges().add(charge);
        return saveRide.execute(ride);
    }
}
