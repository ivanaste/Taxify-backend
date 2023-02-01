package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeMessageStatus {
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final SendMessage sendMessage;
    private final ChangeMessageStatusToSent changeMessageStatusToSent;
    private final ChangeMessageStatusToDelivered changeMessageStatusToDelivered;
    private final ChangeMessageStatusToSeen changeMessageStatusToSeen;
    private final ChangeMessageStatusToReplied changeMessageStatusToReplied;

    public Message execute(Message message, final MessageStatus messageStatus) {
        User user = getUserByEmail.execute(getSelf.execute().getEmail());
        User receiver = message.getReceiver();
        switch (messageStatus) {
            case SENT -> message = changeMessageStatusToSent.execute(message);
            case DELIVERED -> message = changeMessageStatusToDelivered.execute(message);
            case SEEN -> message = changeMessageStatusToSeen.execute(message, user);
            case REPLIED -> message = changeMessageStatusToReplied.execute(message);
            default -> {
            }
        }
        sendMessage.execute(receiver != null ? receiver.getEmail() : null, "Message changed status");
        return message;
    }
}
