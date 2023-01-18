package com.kts.taxify.services.simulations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.dto.request.simulation.RidingSimulationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SimulateRide {
    private final ObjectMapper objectMapper;


    public boolean execute(RidingSimulationRequest ridingSimulationRequest) throws IOException, InterruptedException {
        String dataString = objectMapper.writeValueAsString(ridingSimulationRequest);
        Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_ride.py", "--conf", "vehicleMovementScripts/locust.conf", "--data", dataString).start();
        int exitVal = p.waitFor();
        return true;
    }
}
