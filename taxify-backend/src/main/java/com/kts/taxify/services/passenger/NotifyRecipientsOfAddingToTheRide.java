package com.kts.taxify.services.passenger;

import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationStatus;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.notification.SaveNotification;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyRecipientsOfAddingToTheRide {

	private final SimpMessagingTemplate simpMessagingTemplate;

	private final SaveNotification saveNotification;

	private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

	public void execute(Set<Passenger> recipients, Passenger sender, Ride ride) {
		for (Passenger recipient : recipients) {
			final Notification notification = Notification.builder().sender(sender).type(NotificationType.ADDED_TO_THE_RIDE).recipient(recipient)
				.arrivalTime(LocalDateTime.now()).read(false).status(NotificationStatus.PENDING).ride(ride)
				.build();
			saveNotification.execute(notification);
			notifyPassengerOfChangedRideState.execute(recipient.getEmail(), NotificationType.ADDED_TO_THE_RIDE);
		}
	}
}
