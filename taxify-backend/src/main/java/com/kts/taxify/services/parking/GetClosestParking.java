package com.kts.taxify.services.parking;

import com.kts.taxify.exception.NoParkingException;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Parking;
import com.kts.taxify.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetClosestParking {
    private final ParkingRepository parkingRepository;

    public Parking execute(@Valid final Location location) {
        List<Parking> allParking = parkingRepository.findAll();
        if (allParking.isEmpty()) {
            throw new NoParkingException();
        }
        Parking closestParking = allParking.get(0);
        Double closestParkingDistance = location.distanceTo(closestParking.getLocation());
        for (Parking parkingPlace : allParking) {
            Double parkingDistance = location.distanceTo(parkingPlace.getLocation());
            if (parkingDistance < closestParkingDistance) {
                closestParking = parkingPlace;
                closestParkingDistance = parkingDistance;
            }
        }
        return closestParking;
    }
}
