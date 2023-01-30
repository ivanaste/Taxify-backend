package com.kts.taxify.services.passenger;

import com.kts.taxify.model.*;
import com.kts.taxify.services.notification.SaveNotification;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotifyPassengerOfChangedRideState {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SaveNotification saveNotification;

    private final GetUserByEmail getUserByEmail;

    public Notification execute(String recipientEmail, NotificationType notificationType) {
        final Notification notification = Notification.builder().type(notificationType).recipient((Passenger) getUserByEmail.execute(recipientEmail))
                .arrivalTime(LocalDateTime.now()).read(false).status(NotificationStatus.PENDING)
                .build();
        saveNotification.execute(notification);
        simpMessagingTemplate.convertAndSend("/topic/passenger-notification/" + recipientEmail,
                notificationType.toString());
        return notification;
    }
}
