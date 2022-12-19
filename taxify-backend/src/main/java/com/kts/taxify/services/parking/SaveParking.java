package com.kts.taxify.services.parking;

import com.kts.taxify.model.Parking;
import com.kts.taxify.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveParking {

    private final ParkingRepository parkingRepository;

    @Transactional
    public Parking execute(final Parking parking) {
        return parkingRepository.save(parking);
    }
}