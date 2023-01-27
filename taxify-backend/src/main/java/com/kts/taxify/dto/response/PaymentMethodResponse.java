package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodResponse {
    @NotEmpty
    String id;

    @NotEmpty
    String type;

    @NotEmpty
    CardResponse card;

}

