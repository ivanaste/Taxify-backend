package com.kts.taxify.services.checkout;


import com.kts.taxify.configProperties.CustomProperties;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class RemovePaymentMethodFromCustomer {
    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public PaymentMethod execute(final String paymentMethodId) throws StripeException {
        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
        return paymentMethod.detach();
    }
}
