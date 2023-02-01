package com.kts.taxify.services.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyAdminsOfMessageArrival {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void execute() {
        simpMessagingTemplate.convertAndSend("/topic/admin-required", "Customer support required...");
    }
}
