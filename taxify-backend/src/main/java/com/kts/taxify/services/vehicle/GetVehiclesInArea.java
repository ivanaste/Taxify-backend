package com.kts.taxify.services.vehicle;

import com.kts.taxify.converter.VehicleConverter;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetVehiclesInArea {

    private final VehicleRepository vehicleRepository;

    public Collection<VehicleResponse> execute(final Double minLongitude,
                                               final Double maxLongitude,
                                               final Double minLatitude,
                                               final Double maxLatitude) {
        final Collection<Vehicle> vehicles = vehicleRepository.findAll();
        Set<VehicleResponse> vehiclesInArea = new HashSet<>();
        vehicles.forEach(vehicle -> {
            if (this.vehicleIsInArea(vehicle.getLocation(), minLongitude, maxLongitude, minLatitude, maxLatitude)) {
                vehiclesInArea.add(VehicleConverter.toVehicleResponse(vehicle));
            }
        });
        return vehiclesInArea;
    }

    private boolean vehicleIsInArea(final Location vehicleLocation,
                                    final Double minLongitude,
                                    final Double maxLongitude,
                                    final Double minLatitude,
                                    final Double maxLatitude) {
        if (vehicleLocation == null) {
            return true;
        }
        final Double vehicleLongitude = vehicleLocation.getLongitude();
        final Double vehicleLatitude = vehicleLocation.getLatitude();
        return (minLongitude < vehicleLongitude && vehicleLongitude < maxLongitude) && (minLatitude < vehicleLatitude && vehicleLatitude < maxLatitude);
    }
}
