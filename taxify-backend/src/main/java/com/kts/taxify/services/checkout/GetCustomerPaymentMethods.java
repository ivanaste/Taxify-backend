package com.kts.taxify.services.checkout;

import com.kts.taxify.configProperties.CustomProperties;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.CustomerListPaymentMethodsParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class GetCustomerPaymentMethods {
    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public PaymentMethodCollection execute(final String customerId) throws StripeException {
        final Customer customer = Customer.retrieve(customerId);
        final CustomerListPaymentMethodsParams params = CustomerListPaymentMethodsParams.builder()
                .setType(CustomerListPaymentMethodsParams.Type.CARD)
                .build();

        return customer.listPaymentMethods(params);
    }
}
