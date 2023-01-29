package com.kts.taxify.services.simulations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.model.Waypoint;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.simulatorModel.Data;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimulateVehicleToClient {
	private final ObjectMapper objectMapper;
	private final GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;
	private final GetSelf getSelf;

	private final GetUserByEmail getUserByEmail;

	@Transactional
	public void execute() throws IOException, InterruptedException {
		Driver driver = (Driver) getUserByEmail.execute(getSelf.execute().getEmail());

		Location clientLocation = null;
		for (Ride ride : driver.getRides()) {
			if (ride.getStatus().equals(RideStatus.ACCEPTED)) {
				Waypoint waypoint = ride.getRoute().getWaypoints().stream().findFirst().get();
				clientLocation = waypoint.getLocation();
			}
		}
		Data data = new Data(driver.getVehicle().getId().toString(), driver.getVehicle().getLocation(), clientLocation);
		String dataStringMacOS = objectMapper.writeValueAsString(data);
		String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
		Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_to_client.py", "--conf", "vehicleMovementScripts/locust.conf", "--data",
			dataStringWindowsOS).start();
		int exitVal = p.waitFor();
	}

}
