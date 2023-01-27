package com.kts.taxify.services.auth;

import com.kts.taxify.converter.PaymentMethodConverter;
import com.kts.taxify.dto.response.PaymentMethodResponse;
import com.kts.taxify.services.checkout.GetCustomerPaymentMethods;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSelfPaymentMethods {
    private final GetSelfAsPassenger getSelfAsPassenger;

    private final GetCustomerPaymentMethods getCustomerPaymentMethods;

    public Collection<PaymentMethodResponse> execute() throws StripeException {
        final List<PaymentMethod> paymentMethods = getCustomerPaymentMethods.execute(getSelfAsPassenger.execute().getCustomerId()).getData();
        List<PaymentMethodResponse> paymentMethodResponses = new ArrayList<>();
        for (PaymentMethod paymentMethod : paymentMethods) {
            paymentMethodResponses.add(PaymentMethodConverter.toPaymentMethodResponse(paymentMethod));
        }
        return paymentMethodResponses;
    }
}
