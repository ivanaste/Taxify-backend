package com.kts.taxify.converter;

import com.kts.taxify.dto.response.PaymentMethodResponse;
import com.stripe.model.PaymentMethod;

public class PaymentMethodConverter {
    public static PaymentMethodResponse toPaymentMethodResponse(final PaymentMethod paymentMethod) {
        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
        paymentMethodResponse.setType(paymentMethod.getType());
        paymentMethodResponse.setId(paymentMethod.getId());
        paymentMethodResponse.setCard(CardConverter.toCardResponse(paymentMethod.getCard()));
        return paymentMethodResponse;
    }

}
