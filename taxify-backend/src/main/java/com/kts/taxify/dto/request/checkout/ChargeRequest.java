package com.kts.taxify.dto.request.checkout;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChargeRequest {
    Double amount;
    String paymentMethodId;
    String customerId;
}
