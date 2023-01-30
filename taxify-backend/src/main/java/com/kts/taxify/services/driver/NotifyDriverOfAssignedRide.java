package com.kts.taxify.services.driver;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyDriverOfAssignedRide {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public void execute(String driverEmail) {
		simpMessagingTemplate.convertAndSend("/topic/driver/" + driverEmail, "A ride has been assigned to you...");
	}
}
