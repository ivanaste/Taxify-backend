package com.kts.taxify.services.notification;

import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationStatus;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateAdminNotification {
    private final SaveNotification saveNotification;
    private final NotifyAdminsOfMessageArrival notifyAdminsOfMessageArrival;

    public Notification execute(User sender) {
        final Notification notification = Notification.builder()
                .sender(sender)
                .arrivalTime(LocalDateTime.now())
                .read(false)
                .status(NotificationStatus.PENDING)
                .type(NotificationType.CUSTOMER_SUPPORT_REQUIRED)
                .build();
        notifyAdminsOfMessageArrival.execute();
        return saveNotification.execute(notification);
    }
}
