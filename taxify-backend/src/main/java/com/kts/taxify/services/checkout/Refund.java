package com.kts.taxify.services.checkout;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.services.auth.GetSelfAsPassenger;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.RefundCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class Refund {
    private final GetSelfAsPassenger getSelfAsPassenger;

    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public com.stripe.model.Refund execute(Double amount, String customerId, String paymentId) throws StripeException {
        RefundCreateParams params = RefundCreateParams.builder()
                .setCurrency("EUR")
                .setAmount(Long.parseLong(amount.toString()))
                .setCustomer(customerId)
                .setPaymentIntent(paymentId)
                .build();
        return com.stripe.model.Refund.create(params);
    }
}
