package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    String brand;
    String expMonth;
    String expYear;
    String last4;
    String issuer;
}
