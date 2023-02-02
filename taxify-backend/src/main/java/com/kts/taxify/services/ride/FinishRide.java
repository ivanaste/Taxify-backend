package com.kts.taxify.services.ride;

import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinishRide {

    private final GetDriverAssignedRide getDriverAssignedRide;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;
    private final NotifyDriver notifyDriver;


    public void execute() {
        Ride ride = getDriverAssignedRide.execute();
        ride.setStatus(RideStatus.FINISHED);
        ride.getDriver().getVehicle().setOccupied(false);
        saveRide.execute(ride);
        notifyDriver.execute(ride.getDriver().getEmail(), NotificationType.RIDE_FINISHED_DRIVER);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_FINISHED_PASSENGER);
    }
}
