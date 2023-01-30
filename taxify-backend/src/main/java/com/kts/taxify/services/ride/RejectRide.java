package com.kts.taxify.services.ride;

import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.simulations.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectRide {

    private final GetRideById getRideById;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    private final SimulationService simulationService;

    private final GetDriverAssignedRide getDriverAssignedRide;

    public void execute(String rejectionReason) {
        Ride ride = getDriverAssignedRide.execute();
        Process currentProcess = simulationService.activeProcesses.get(ride.getId());
        currentProcess.destroy();
        ride.setStatus(RideStatus.REJECTED);
        ride.setRejectionReason(rejectionReason);
        //passenger na unoccupied
        ride.getDriver().getVehicle().setOccupied(false);
        saveRide.execute(ride);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_REJECTED);
    }
}
