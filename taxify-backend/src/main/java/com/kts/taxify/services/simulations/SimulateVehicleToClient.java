package com.kts.taxify.services.simulations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.dto.response.RideResponse;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.model.Waypoint;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.ride.GetDriverAssignedRide;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.simulatorModel.ToClientData;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimulateVehicleToClient {
	private final ObjectMapper objectMapper;
	private final GetDriverAssignedRide getDriverAssignedRide;

	@Transactional
	public void execute() throws IOException, InterruptedException {
		Ride ride = getDriverAssignedRide.execute();
		Location clientLocation = ride.getRoute().getWaypoints().get(0).getLocation();
		ToClientData toClientData = new ToClientData(ride.getDriver().getVehicle().getId().toString(), ride.getDriver().getVehicle().getLocation(), clientLocation);
		String dataStringMacOS = objectMapper.writeValueAsString(toClientData);
		String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
		Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_to_client.py", "--conf", "vehicleMovementScripts/locust.conf", "--data",
			dataStringWindowsOS).start();
		int exitVal = p.waitFor();
	}

}
