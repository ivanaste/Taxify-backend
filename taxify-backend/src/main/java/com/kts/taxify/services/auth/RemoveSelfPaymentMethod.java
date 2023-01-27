package com.kts.taxify.services.auth;

import com.kts.taxify.converter.PaymentMethodConverter;
import com.kts.taxify.dto.response.PaymentMethodResponse;
import com.kts.taxify.services.checkout.RemovePaymentMethodFromCustomer;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveSelfPaymentMethod {
    private final RemovePaymentMethodFromCustomer removePaymentMethodFromCustomer;

    public PaymentMethodResponse execute(final String paymentMethodId) throws StripeException {
        return PaymentMethodConverter.toPaymentMethodResponse(removePaymentMethodFromCustomer.execute(paymentMethodId));
    }
}
