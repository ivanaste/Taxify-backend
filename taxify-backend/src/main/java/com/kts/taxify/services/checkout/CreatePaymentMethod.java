package com.kts.taxify.services.checkout;

import com.kts.taxify.configProperties.CustomProperties;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CreatePaymentMethod {
    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public PaymentMethod execute(final PaymentMethodCreateParams.CardDetails cardDetails) throws StripeException {
        PaymentMethodCreateParams params = PaymentMethodCreateParams.builder()
                .setCard(cardDetails)
                .setType(PaymentMethodCreateParams.Type.CARD)
                .build();
        return PaymentMethod.create(params);
    }
}
