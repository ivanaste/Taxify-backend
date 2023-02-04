package com.kts.taxify.services.simulations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.exception.AllDriversBusyAndReservedException;
import com.kts.taxify.exception.NoActiveDriversException;
import com.kts.taxify.model.*;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.ride.ChangeRideStatus;
import com.kts.taxify.services.ride.GetDriverAssignedRide;
import com.kts.taxify.services.ride.GetRideById;
import com.kts.taxify.services.ride.SaveRide;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.simulatorModel.FromClientToDestinationData;
import com.kts.taxify.simulatorModel.RideProcessData;
import com.kts.taxify.simulatorModel.ToClientData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class SimulationService {

    public Map<UUID, RideProcessData> activeProcesses = new HashMap<>();
    private final ObjectMapper objectMapper;
    private final GetDriverAssignedRide getDriverAssignedRide;
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;
    private final NotifyDriver notifyDriver;
    private final ChangeRideStatus changeRideStatus;
    private final GetRideById getRideById;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public Map<UUID, RideProcessData> getActiveProcesses() {
        return activeProcesses;
    }


    private List<Ride> getListOfActiveRidesWithSatisfactoryDriver(RequestedRideRequest requestedRideRequest) {
        List<Ride> rides = new ArrayList<>();
        for (UUID key : activeProcesses.keySet()) {
            Ride ride = getRideById.execute(key);
            Driver driver = ride.getDriver();
            if (!driver.isReserved() && requestedRideRequest.getVehicleTypes().contains(driver.getVehicle().getType().getName())) {
                if (requestedRideRequest.isBabyFriendly() && requestedRideRequest.isPetFriendly()) {
                    if (driver.getVehicle().getBabyFriendly() && driver.getVehicle().getPetFriendly()) {
                        rides.add(ride);
                    }
                } else if (requestedRideRequest.isBabyFriendly()) {
                    if (driver.getVehicle().getBabyFriendly()) {
                        rides.add(ride);
                    }
                } else if (requestedRideRequest.isPetFriendly()) {
                    if (driver.getVehicle().getPetFriendly()) {
                        rides.add(ride);
                    }
                } else {
                    rides.add(ride);
                }
            }
        }
        return rides;
    }


    @Transactional
    public Driver searchForFirstFreeDriver(RequestedRideRequest requestedRideRequest) throws InterruptedException, ExecutionException {
        if (activeProcesses.size() == 0) throw new NoActiveDriversException();
        Process first = null;
        Duration shortest = Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);
        Driver firstFreeDriver = null;
        List<Ride> activeRidesWithSatisfactoryDriver = getListOfActiveRidesWithSatisfactoryDriver(requestedRideRequest);
        for(Ride ride: activeRidesWithSatisfactoryDriver) {
            if (Duration.between(activeProcesses.get(ride.getId()).getExpectedEnd(), activeProcesses.get(ride.getId()).getStart()).compareTo(shortest) < 0) {
                shortest = Duration.between(activeProcesses.get(ride.getId()).getExpectedEnd(), activeProcesses.get(ride.getId()).getStart());
                first = activeProcesses.get(ride.getId()).getProcess();
                firstFreeDriver = ride.getDriver();
            }
        }
        if (Objects.isNull(first)) throw new AllDriversBusyAndReservedException();
        notifyPassengerOfChangedRideState.execute(requestedRideRequest.getPassengers().getSenderEmail(), NotificationType.RESERVED_DRIVER);
        for(String email: requestedRideRequest.getPassengers().getRecipientsEmails()) {
            notifyPassengerOfChangedRideState.execute(email, NotificationType.RESERVED_DRIVER);
        }
        firstFreeDriver.setReserved(true);
        driverRepository.save(firstFreeDriver);
        first.waitFor();
        return firstFreeDriver;
    }

    public int simulateRideToClient() throws IOException, InterruptedException {
        Ride ride = getDriverAssignedRide.execute();
        Location clientLocation = ride.getRoute().getWaypoints().get(0).getLocation();
        ride.setScheduledAt(LocalDateTime.now());
        saveRide.execute(ride);
        ToClientData toClientData = new ToClientData(ride.getDriver().getVehicle().getId().toString(), ride.getDriver().getVehicle().getLocation(), clientLocation);
        String dataStringMacOS = objectMapper.writeValueAsString(toClientData);
        String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
        Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_to_client.py", "--conf", "vehicleMovementScripts/locust.conf", "--data",
                dataStringWindowsOS).start();
        activeProcesses.put(ride.getId(), new RideProcessData(p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(ride.getRoute().getWaypoints().size()).plusSeconds(60)));
        int exitVal = p.waitFor();
        activeProcesses.remove(ride.getId());
        return exitVal;
    }

    public boolean simulateRideFromClientToDestination() throws IOException, InterruptedException {
        Driver driver = (Driver) getUserByEmail.execute(getSelf.execute().getEmail());
        Ride ride = getDriverAssignedRide.execute();
        ride.setStatus(RideStatus.STARTED);
        saveRide.execute(ride);
        for(Passenger passenger: ride.getPassengers()) {
            notifyPassengerOfChangedRideState.execute(passenger.getEmail(), NotificationType.RIDE_STARTED);
        }
        FromClientToDestinationData data = new FromClientToDestinationData(driver.getVehicle().getId().toString(), ride.getRoute().getWaypoints());
        String dataStringMacOS = objectMapper.writeValueAsString(data);
        String dataStringWindowsOS = dataStringMacOS.replace("\"", "\\\"");
        Process p = new ProcessBuilder("locust", "-f", "vehicleMovementScripts/simulate_ride.py", "--conf", "vehicleMovementScripts/locust.conf", "--data", dataStringWindowsOS).start();
        activeProcesses.put(ride.getId(), new RideProcessData(p, LocalDateTime.now(), LocalDateTime.now().plusSeconds(ride.getRoute().getWaypoints().size())));
        int exitVal = p.waitFor();
        activeProcesses.remove(ride.getId());
        changeRideStatus.execute(RideStatus.ON_DESTINATION, NotificationType.ON_DESTINATION_PASSENGER);
        notifyDriver.execute(driver.getEmail(), NotificationType.ON_DESTINATION_DRIVER);
        return true;
    }
}
