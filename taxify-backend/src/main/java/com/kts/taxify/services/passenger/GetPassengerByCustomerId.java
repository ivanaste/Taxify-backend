package com.kts.taxify.services.passenger;

import com.kts.taxify.exception.UserNotFoundException;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPassengerByCustomerId {
    private final PassengerRepository passengerRepository;

    // TODO: create exception for missmatch customerId
    @Transactional(readOnly = true)
    public Passenger execute(final String customerId) {
        return passengerRepository.findByCustomerId(customerId).orElseThrow(UserNotFoundException::new);
    }

}
