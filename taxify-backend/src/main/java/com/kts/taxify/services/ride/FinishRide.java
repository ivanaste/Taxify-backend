package com.kts.taxify.services.ride;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinishRide {

    private final GetRideById getRideById;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    public void execute(UUID assignedRideId) {
        Ride ride = getRideById.execute(assignedRideId);
        ride.setStatus(RideStatus.FINISHED);
        ride.getDriver().getVehicle().setOccupied(false);
        saveRide.execute(ride);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_FINISHED);
    }
}
