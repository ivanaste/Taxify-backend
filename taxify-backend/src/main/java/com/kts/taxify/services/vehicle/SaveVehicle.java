package com.kts.taxify.services.vehicle;

import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveVehicle {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public Vehicle execute(final Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
