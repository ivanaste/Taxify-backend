package com.kts.taxify.services.ride;

import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.checkout.RefundChargedPassengersInRide;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.simulations.FindActiveProcess;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectRide {
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;
    private final GetDriverAssignedRide getDriverAssignedRide;
    private final FindActiveProcess findActiveProcess;
    private final RefundChargedPassengersInRide refundChargedPassengersInRide;

    public void execute(String rejectionReason) throws StripeException {
        Ride ride = getDriverAssignedRide.execute();
//<<<<<<< Updated upstream
//        Process currentProcess = simulationService.activeProcesses.get(ride.getId()).getProcess();
//=======
        Process currentProcess = findActiveProcess.execute(ride.getId());
        currentProcess.destroy();
        ride.setStatus(RideStatus.REJECTED);
        ride.setRejectionReason(rejectionReason);
        //passenger na unoccupied
        ride.getDriver().getVehicle().setOccupied(false);
        ride = saveRide.execute(ride);
        refundChargedPassengersInRide.execute(ride);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_REJECTED);
    }
}
