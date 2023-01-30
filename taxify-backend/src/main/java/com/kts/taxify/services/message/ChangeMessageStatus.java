package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangeMessageStatus {
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final SaveMessage saveMessage;
    private final SaveUser saveUser;

    public Message execute(Message message, final MessageStatus messageStatus) {
        User user = getUserByEmail.execute(getSelf.execute().getEmail());
        message.setStatus(messageStatus);
        switch (messageStatus) {
            case DELIVERED -> message.setDeliveredOn(LocalDateTime.now());
            case SEEN -> message.setSeenOn(LocalDateTime.now());
            case REPLIED -> message.setRepliedOn(LocalDateTime.now());
            default -> message.setCreatedOn(LocalDateTime.now());
        }
        if (message.getReceiver() == null && user.getRole().getName().equals("ADMIN")) {
            message.setReceiver(user);
        }
        Message response = saveMessage.execute(message);
        if (message.getReceiver() == null && user.getRole().getName().equals("ADMIN")) {
            user.getReceivedMessages().add(response);
            saveUser.execute(user);
        }
        return response;
    }
}
