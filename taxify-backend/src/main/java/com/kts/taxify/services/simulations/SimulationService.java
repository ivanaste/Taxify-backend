package com.kts.taxify.services.simulations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.model.*;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.ride.GetDriverAssignedRide;
import com.kts.taxify.services.ride.GetRideById;
import com.kts.taxify.services.ride.SaveRide;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.simulatorModel.FromClientToDestinationData;
import com.kts.taxify.simulatorModel.ToClientData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimulationService {

    public Map<UUID, Process> activeProcesses = new HashMap<>();

    private final ObjectMapper objectMapper;
    private final GetDriverAssignedRide getDriverAssignedRide;
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final GetRideById getRideById;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    public int simulateRideToClient() throws IOException, InterruptedException {
        Ride ride = getDriverAssignedRide.execute();
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_ACCEPTED);
        Location clientLocation = ride.getRoute().getWaypoints().get(0).getLocation();
        ToClientData toClientData = new ToClientData(ride.getDriver().getVehicle().getId().toString(), ride.getDriver().getVehicle().getLocation(), clientLocation);
        String dataStringMacOS = objectMapper.writeValueAsString(toClientData);
        String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
        Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_to_client.py", "--conf", "vehicleMovementScripts/locust.conf", "--data",
                dataStringMacOS).start();
        activeProcesses.put(ride.getId(), p);
        int exitVal = p.waitFor();
        return exitVal;
    }

    public boolean simulateRideFromClientToDestination() throws IOException, InterruptedException {
        Driver driver = (Driver) getUserByEmail.execute(getSelf.execute().getEmail());
        Ride ride = getDriverAssignedRide.execute();
        ride.setStatus(RideStatus.STARTED);
        saveRide.execute(ride);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_STARTED);
        FromClientToDestinationData data = new FromClientToDestinationData(driver.getVehicle().getId().toString(), ride.getRoute().getWaypoints());
        String dataStringMacOS = objectMapper.writeValueAsString(data);
        String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
        Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_ride.py", "--conf", "vehicleMovementScripts/locust.conf", "--data", dataStringMacOS).start();
        activeProcesses.put(ride.getId(), p);
        int exitVal = p.waitFor();
        return true;
    }
}
