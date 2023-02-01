package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import com.kts.taxify.model.Notification;
import com.kts.taxify.model.User;
import com.kts.taxify.services.notification.SaveNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangeMessageStatusToSeen {
    private final AddMessageToReceiver addMessageToReceiver;
    private final SaveNotification saveNotification;
    private final SaveMessage saveMessage;

    public Message execute(Message message, User self) {
        message.setStatus(MessageStatus.SEEN);
        message.setSeenOn(LocalDateTime.now());
        if (self.getRole().getName().equals("ADMIN")) {
            message.setReceiver(self);
            Notification notification = message.getNotification();
            if (notification != null) {
                notification.setRead(true);
                message.setNotification(saveNotification.execute(notification));
            }
            message = saveMessage.execute(message);
            addMessageToReceiver.execute(self, message);
            return message;
        }
        return saveMessage.execute(message);
    }
}
