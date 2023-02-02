package com.kts.taxify.services.checkout;

import com.kts.taxify.model.Charge;
import com.kts.taxify.repository.ChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCharge {
    private final ChargeRepository chargeRepository;

    public Charge execute(Charge charge) {
        return chargeRepository.save(charge);
    }
}
