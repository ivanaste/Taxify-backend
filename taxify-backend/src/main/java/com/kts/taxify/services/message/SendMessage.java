package com.kts.taxify.services.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class SendMessage {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void execute(@Nullable final String recipientEmail, final String senderEmail) {
        simpMessagingTemplate.convertAndSend("/topic/message/" + (recipientEmail == null ? "admin" : recipientEmail), senderEmail);
    }
}
