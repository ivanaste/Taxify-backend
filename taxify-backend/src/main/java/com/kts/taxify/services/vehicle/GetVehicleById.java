package com.kts.taxify.services.vehicle;

import com.kts.taxify.exception.VehicleNotFoundException;
import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetVehicleById {
    private final VehicleRepository vehicleRepository;

    public Vehicle execute(final UUID id) {
        return vehicleRepository.findById(id).orElseThrow(VehicleNotFoundException::new);
    }
}
