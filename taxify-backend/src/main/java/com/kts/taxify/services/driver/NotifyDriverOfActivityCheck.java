package com.kts.taxify.services.driver;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyDriverOfActivityCheck {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public void execute() {
		simpMessagingTemplate.convertAndSend("/topic/driver", "Remaining working hours check...");
	}
}
