package com.kts.taxify.controller;

import com.kts.taxify.dto.request.simulation.RidingSimulationRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.model.Location;
import com.kts.taxify.services.simulations.SimulateRide;
import com.kts.taxify.services.simulations.SimulateVehicleToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/simulation")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulateVehicleToClient runVehicleToClientScript;
    private final SimulateRide simulateRide;

    @PostMapping(value = "/to-client")
    public DriverResponse simulateVehicleToClient(@RequestBody Location clientLocation) throws IOException, InterruptedException {
        return runVehicleToClientScript.execute(clientLocation);
    }

    @PostMapping("/through-route")
    public boolean moveVehicle(@RequestBody RidingSimulationRequest ridingSimulationRequest) throws IOException, InterruptedException {
        return simulateRide.execute(ridingSimulationRequest);
    }
}
