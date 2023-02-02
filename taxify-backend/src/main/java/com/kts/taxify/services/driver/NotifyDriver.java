package com.kts.taxify.services.driver;

import com.kts.taxify.model.NotificationType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyDriver {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public void execute(String driverEmail, NotificationType notificationType) {
		simpMessagingTemplate.convertAndSend("/topic/driver/" + driverEmail, notificationType.toString());
	}
}
