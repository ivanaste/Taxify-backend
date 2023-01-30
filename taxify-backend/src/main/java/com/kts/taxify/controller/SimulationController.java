package com.kts.taxify.controller;

import com.kts.taxify.services.simulations.SimulateRide;
import com.kts.taxify.services.simulations.SimulateVehicleToClient;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/through-route/{assignedRideId}")
	public boolean moveVehicle(@PathVariable("assignedRideId") UUID assignedRideId) throws IOException, InterruptedException {
		return simulateRide.execute(assignedRideId);
	}
}
