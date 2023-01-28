package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMessage {
    private final MessageRepository messageRepository;

    public Message execute(Message message) {
        return messageRepository.save(message);
    }

}
