package com.kts.taxify.services.passenger;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifySenderOfAcceptedRideByLinkedPassengers {
	private final SimpMessagingTemplate simpMessagingTemplate;

	public void execute(String senderEmail) {
		simpMessagingTemplate.convertAndSend("/topic/acceptedRideByLinkedPassengers/" + senderEmail,
			"All linked users have accepted the ride");
	}
}
