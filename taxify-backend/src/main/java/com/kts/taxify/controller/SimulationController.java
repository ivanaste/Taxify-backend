package com.kts.taxify.controller;

import com.kts.taxify.dto.request.simulation.RidingSimulationRequest;
import com.kts.taxify.services.simulations.SimulateRide;
import com.kts.taxify.services.simulations.SimulateVehicleToClient;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/simulation")
@RequiredArgsConstructor
public class SimulationController {

	private final SimulateVehicleToClient simulateVehicleToClient;
	private final SimulateRide simulateRide;

	@PostMapping(value = "/to-client")
	public void simulateVehicleToClient() throws IOException, InterruptedException {
		simulateVehicleToClient.execute();
	}

	@PostMapping("/through-route")
	public boolean moveVehicle(@RequestBody RidingSimulationRequest ridingSimulationRequest) throws IOException, InterruptedException {
		return simulateRide.execute(ridingSimulationRequest);
	}
}
