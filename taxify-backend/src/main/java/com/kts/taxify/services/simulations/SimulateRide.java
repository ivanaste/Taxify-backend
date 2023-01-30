package com.kts.taxify.services.simulations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.ride.GetRideById;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.simulatorModel.FromClientToDestinationData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimulateRide {
    private final ObjectMapper objectMapper;

    private final GetSelf getSelf;

    private final GetUserByEmail getUserByEmail;

    private final GetRideById getRideById;

    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;
    private final RideRepository rideRepository;
    private final NotifyDriver notifyDriver;


    public boolean execute(UUID assignedRideId) throws IOException, InterruptedException {
        Driver driver = (Driver) getUserByEmail.execute(getSelf.execute().getEmail());
        Ride ride = getRideById.execute(assignedRideId);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_STARTED);
        FromClientToDestinationData data = new FromClientToDestinationData(driver.getVehicle().getId().toString(), ride.getRoute().getWaypoints());
        String dataStringMacOS = objectMapper.writeValueAsString(data);
        String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
        Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_ride.py", "--conf", "vehicleMovementScripts/locust.conf", "--data", dataStringMacOS).start();
        int exitVal = p.waitFor();
        ride.setStatus(RideStatus.FINISHED);
        rideRepository.save(ride);
        notifyDriver.execute(driver.getEmail(), NotificationType.RIDE_FINISHED_DRIVER);
        return true;
    }
}
