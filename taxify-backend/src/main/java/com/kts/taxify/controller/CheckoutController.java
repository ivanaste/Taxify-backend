package com.kts.taxify.controller;

import com.kts.taxify.dto.request.checkout.ChargeRequest;
import com.kts.taxify.dto.response.PaymentResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.checkout.Checkout;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final Checkout checkout;

    @PostMapping("/stripe")
    @HasAnyPermission({Permission.PAYMENT_CHECKOUT})
    public PaymentResponse stripeCheckout(@RequestBody final ChargeRequest chargeRequest) throws StripeException {
        return checkout.execute(chargeRequest);
    }
}
