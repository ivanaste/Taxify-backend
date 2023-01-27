package com.kts.taxify.services.checkout;

import com.kts.taxify.configProperties.CustomProperties;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodAttachParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AddPaymentMethodToCustomer {
    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public PaymentMethod execute(final PaymentMethod paymentMethod, final String customerId) throws StripeException {
        PaymentMethodAttachParams params = PaymentMethodAttachParams.builder()
                .setCustomer(customerId)
                .build();
        return paymentMethod.attach(params);
    }
}
