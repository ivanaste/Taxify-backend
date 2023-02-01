package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangeMessageStatusToDelivered {
    private final SaveMessage saveMessage;

    public Message execute(Message message) {
        message.setStatus(MessageStatus.DELIVERED);
        message.setDeliveredOn(LocalDateTime.now());
        return saveMessage.execute(message);
    }
}
