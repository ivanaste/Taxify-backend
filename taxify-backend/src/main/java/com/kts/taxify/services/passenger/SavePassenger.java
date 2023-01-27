package com.kts.taxify.services.passenger;

import com.kts.taxify.model.Passenger;
import com.kts.taxify.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavePassenger {

    private final PassengerRepository passengerRepository;

    @Transactional(readOnly = false)
    public Passenger execute(final Passenger passenger) {
        return passengerRepository.save(passenger);
    }
}
