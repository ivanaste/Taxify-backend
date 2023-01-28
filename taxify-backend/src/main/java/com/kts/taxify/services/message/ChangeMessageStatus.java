package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangeMessageStatus {
    private final SaveMessage saveMessage;

    public Message execute(Message message, final MessageStatus messageStatus) {
        message.setStatus(messageStatus);
        switch (messageStatus) {
            case DELIVERED -> message.setDeliveredOn(LocalDateTime.now());
            case SEEN -> message.setSeenOn(LocalDateTime.now());
            case REPLIED -> message.setRepliedOn(LocalDateTime.now());
            default -> message.setCreatedOn(LocalDateTime.now());
        }
        return saveMessage.execute(message);
    }
}
