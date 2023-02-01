package com.kts.taxify.services.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyUserOfIsBlockedChange {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void execute(String receiverEmail, String message) {
        simpMessagingTemplate.convertAndSend("/topic/blocked/" + receiverEmail, message);
    }
}
