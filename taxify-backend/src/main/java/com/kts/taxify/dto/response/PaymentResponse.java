package com.kts.taxify.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    String id;
    Long amount;
    String currency;
    Long created;
}
