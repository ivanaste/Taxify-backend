package com.kts.taxify.services.passenger;

import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.ride.GetDriverAssignedRide;
import com.kts.taxify.services.ride.SaveRide;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyPassengerOfVehicleArrived {

    private final GetDriverAssignedRide getDriverAssignedRide;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;


    public void execute() {
        Ride ride = getDriverAssignedRide.execute();
        ride.setStatus(RideStatus.ARRIVED);
        saveRide.execute(ride);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.VEHICLE_ARRIVED);
    }
}
