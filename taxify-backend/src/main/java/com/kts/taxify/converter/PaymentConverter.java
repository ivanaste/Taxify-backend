package com.kts.taxify.converter;

import com.kts.taxify.dto.response.PaymentResponse;
import com.stripe.model.PaymentIntent;

public class PaymentConverter {
    public static PaymentResponse toPaymentResponse(final PaymentIntent paymentIntent) {
        return new PaymentResponse(
                paymentIntent.getId(), paymentIntent.getAmount(), paymentIntent.getCurrency(), paymentIntent.getCreated());
    }
}
