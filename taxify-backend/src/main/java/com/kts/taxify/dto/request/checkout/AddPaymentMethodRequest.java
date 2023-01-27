package com.kts.taxify.dto.request.checkout;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddPaymentMethodRequest {
    String cvc;
    Long expMonth;
    Long expYear;
    String number;
}
