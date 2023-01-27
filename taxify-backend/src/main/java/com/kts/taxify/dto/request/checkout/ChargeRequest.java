package com.kts.taxify.dto.request.checkout;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }

    String description;
    Double amount;
    Currency currency;
    String paymentMethodId;

    public String getCurrency() {
        return currency.name();
    }
}
