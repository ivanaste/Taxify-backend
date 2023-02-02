package com.kts.taxify.services.notification;

import com.kts.taxify.converter.NotificationConverter;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationStatus;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.ride.AddChargeToRide;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcceptAddingToTheRide {
    private final AddChargeToRide addChargeToRide;

    private final GetNotificationById getNotificationById;

    private final SaveNotification saveNotification;

    private final CheckIfAllRideNotificationsAreAccepted checkIfAllRideNotificationsAreAccepted;

    public NotificationResponse execute(UUID notificationId, String paymentMethodId) {
        Notification notification = getNotificationById.execute(notificationId);
        notification.setStatus(NotificationStatus.ACCEPTED);
        notification = saveNotification.execute(notification);

        Ride ride = notification.getRide();
        addChargeToRide.execute(ride, ((Passenger) notification.getRecipient()).getCustomerId(), paymentMethodId, ride.getRoute().getPrice());

        checkIfAllRideNotificationsAreAccepted.execute(notification.getRide(), notification.getSender().getEmail());
        return NotificationConverter.toNotificationResponse(notification);
    }
}
