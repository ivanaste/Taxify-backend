package com.kts.taxify.services.passenger;

import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.notification.SaveNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotifyRecipientsOfAddingToTheRide {
    private final SaveNotification saveNotification;

    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    public void execute(Set<Passenger> recipients, Passenger sender, Ride ride) {
        for (Passenger recipient : recipients) {
            if (!Objects.equals(recipient.getEmail(), sender.getEmail())) {
                Notification notification = notifyPassengerOfChangedRideState.execute(recipient.getEmail(), NotificationType.ADDED_TO_THE_RIDE);
                notification.setRide(ride);
                notification.setSender(sender);
                saveNotification.execute(notification);
            }
        }
    }
}
