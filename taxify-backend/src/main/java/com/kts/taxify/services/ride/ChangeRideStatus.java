package com.kts.taxify.services.ride;

import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeRideStatus {
    private final GetDriverAssignedRide getDriverAssignedRide;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;


    public void execute(RideStatus rideStatus, NotificationType notificationType) {
        Ride ride = getDriverAssignedRide.execute();
        ride.setStatus(rideStatus);
        saveRide.execute(ride);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), notificationType);
    }
}
