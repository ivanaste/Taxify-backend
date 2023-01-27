package com.kts.taxify.converter;

import com.kts.taxify.dto.response.CardResponse;
import com.stripe.model.PaymentMethod;
import org.modelmapper.ModelMapper;

public class CardConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static CardResponse toCardResponse(final PaymentMethod.Card card) {
        return modelMapper.map(card, CardResponse.class);
    }
}
