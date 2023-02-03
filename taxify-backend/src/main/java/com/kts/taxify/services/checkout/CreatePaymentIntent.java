package com.kts.taxify.services.checkout;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.services.auth.GetSelfAsPassenger;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CreatePaymentIntent {
    private final GetSelfAsPassenger getSelfAsPassenger;

    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public PaymentIntent execute(final Charge charge, Passenger passenger) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency("EUR")
                .setAmount(charge.getAmount().longValue() * 100)
                .setConfirm(true)
                .setPaymentMethod(charge.getPaymentMethodId())
                .setCustomer(passenger.getCustomerId())
                .setReceiptEmail(passenger.getEmail())
                .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.AUTOMATIC)
                .build();
        return PaymentIntent.create(params);
    }
}
