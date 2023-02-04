package com.kts.taxify.services.vehicle;

import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.VehicleRepository;
import com.kts.taxify.services.driver.SetDriverVehicleAssOccupied;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetVehicleAsOccupiedTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private SetDriverVehicleAssOccupied setDriverVehicleAssOccupied;

    @Captor
    private ArgumentCaptor<Vehicle> vehicleArgumentCaptor;

    @Test
    @DisplayName("Should mark vehicle as occupied")
    public void shouldMarkVehicleAsOccupied() throws IOException, InterruptedException {

        Vehicle vehicle = Vehicle.builder().occupied(false).build();

        setDriverVehicleAssOccupied.execute(vehicle);

        assertThat(vehicle.getOccupied()).isEqualTo(true);
        verify(vehicleRepository, times(1)).save(vehicleArgumentCaptor.capture());
        assertThat(vehicle.getOccupied()).isEqualTo(vehicleArgumentCaptor.getValue().getOccupied());
        verifyNoMoreInteractions(vehicleRepository);
    }

}
