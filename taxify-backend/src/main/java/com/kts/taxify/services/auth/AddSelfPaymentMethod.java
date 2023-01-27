package com.kts.taxify.services.auth;

import com.kts.taxify.converter.PaymentMethodConverter;
import com.kts.taxify.dto.request.checkout.AddPaymentMethodRequest;
import com.kts.taxify.dto.response.PaymentMethodResponse;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.services.checkout.AddPaymentMethodToCustomer;
import com.kts.taxify.services.checkout.CreatePaymentMethod;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddSelfPaymentMethod {
    private final GetSelfAsPassenger getSelfAsPassenger;

    private final CreatePaymentMethod createPaymentMethod;

    private final AddPaymentMethodToCustomer addPaymentMethodToCustomer;


    @Transactional(readOnly = true)
    public PaymentMethodResponse execute(final AddPaymentMethodRequest addPaymentMethodRequest) throws StripeException {
        final PaymentMethodCreateParams.CardDetails cardDetails = PaymentMethodCreateParams.CardDetails.builder()
                .setCvc(addPaymentMethodRequest.getCvc())
                .setExpMonth(addPaymentMethodRequest.getExpMonth())
                .setExpYear(addPaymentMethodRequest.getExpYear())
                .setNumber(addPaymentMethodRequest.getNumber())
                .build();
        PaymentMethod paymentMethod = createPaymentMethod.execute(cardDetails);
        final Passenger passenger = getSelfAsPassenger.execute();
        return PaymentMethodConverter.toPaymentMethodResponse(addPaymentMethodToCustomer.execute(paymentMethod, passenger.getCustomerId()));
    }
}
