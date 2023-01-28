package com.kts.taxify.services.passenger;

import com.kts.taxify.model.NotificationType;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyRecipientsOfAddingToTheRide {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public void execute(String recipientEmail) {
		simpMessagingTemplate.convertAndSend("/topic/passenger-notification/" + recipientEmail,
			NotificationType.ADDED_TO_THE_RIDE.toString());
	}
}
