package com.kts.taxify.controller;

import com.kts.taxify.services.simulations.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/simulation")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping(value = "/to-client")
    public int simulateVehicleToClient() throws IOException, InterruptedException {
        return simulationService.simulateRideToClient();
    }

    @PostMapping("/through-route")
    public boolean moveVehicle() throws IOException, InterruptedException {
        return simulationService.simulateRideFromClientToDestination();
    }
}
