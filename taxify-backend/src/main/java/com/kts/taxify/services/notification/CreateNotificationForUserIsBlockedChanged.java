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
public class CreateNotificationForUserIsBlockedChanged {
    private final SaveNotification saveNotification;
    private final NotifyUserOfIsBlockedChange notifyUserOfIsBlockedChange;

    public Notification execute(User admin, User receiver, String reason) {
        final Notification notification = Notification.builder()
                .sender(admin)
                .recipient(receiver)
                .arrivalTime(LocalDateTime.now())
                .read(false)
                .status(NotificationStatus.PENDING)
                .type(NotificationType.USER_STATUS_CHANGE)
                .userStatusChangeReason(reason)
                .build();
        notifyUserOfIsBlockedChange.execute(receiver.getEmail(), "You have been " + (receiver.isBlocked() ? "blocked" : "unblocked") + " by our admin with the explanation: '" + reason + "'");
        return saveNotification.execute(notification);
    }
}
