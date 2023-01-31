package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveMessage {
    private final MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public Message execute(Message message) {
        return messageRepository.save(message);
    }

}
