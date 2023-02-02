package com.kts.taxify.services.ride;

import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FinishRide {

    private final GetOnDestinationRideDriver getOnDestinationRideDriver;
    private final SaveRide saveRide;
    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;
    private final NotifyDriver notifyDriver;


    @Transactional
    public void execute() {
        Ride ride = getOnDestinationRideDriver.execute();
        ride.setStatus(RideStatus.FINISHED);
        ride.getDriver().getVehicle().setOccupied(false);
        ride.setFinishedAt(LocalDateTime.now());
        saveRide.execute(ride);
        notifyDriver.execute(ride.getDriver().getEmail(), NotificationType.RIDE_FINISHED_DRIVER);
        notifyPassengerOfChangedRideState.execute(ride.getSender(), NotificationType.RIDE_FINISHED_PASSENGER);
    }
}
