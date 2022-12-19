package com.kts.taxify.services.parking;

import com.kts.taxify.model.Location;
import com.kts.taxify.model.Parking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class CreateParking {

    private final SaveParking saveParking;

    public Parking execute(@Valid final Location parkingLocation) {
        final Parking parking = Parking.builder().location(parkingLocation).build();
        return saveParking.execute(parking);
    }
}