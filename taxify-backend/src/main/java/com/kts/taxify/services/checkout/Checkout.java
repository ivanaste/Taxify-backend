package com.kts.taxify.services.checkout;

import com.kts.taxify.converter.PaymentConverter;
import com.kts.taxify.dto.request.checkout.ChargeRequest;
import com.kts.taxify.dto.response.PaymentResponse;
import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Passenger;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Checkout {
    private final CreatePaymentIntent createPaymentIntent;

    public PaymentResponse execute(final Charge charge, final Passenger passenger) throws StripeException {
        return PaymentConverter.toPaymentResponse(createPaymentIntent.execute(charge, passenger));
    }
}
