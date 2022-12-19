package com.kts.taxify.services.parking;

import com.kts.taxify.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteParking {

    private final ParkingRepository parkingRepository;

    public void execute(@Valid final UUID parkingId) {
        parkingRepository.deleteById(parkingId);
    }
}
