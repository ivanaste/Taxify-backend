package com.kts.taxify.services.checkout;

import com.kts.taxify.configProperties.CustomProperties;
import com.kts.taxify.model.Passenger;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CreateStripeCustomer {
    private final CustomProperties customProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = customProperties.getStripeSecret();
    }

    public Customer execute(Passenger passenger) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(passenger.getEmail())
                .setName(passenger.getName() + " " + passenger.getSurname())
                .setPhone(passenger.getPhoneNumber())
                .build();
        return Customer.create(params);
    }
}
