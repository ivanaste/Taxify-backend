package com.kts.taxify.services.notification;

import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationStatus;
import com.kts.taxify.model.Ride;
import com.kts.taxify.services.passenger.NotifySenderOfAcceptedRideByLinkedPassengers;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckIfAllRideNotificationsAreAccepted {

	private final NotifySenderOfAcceptedRideByLinkedPassengers notifySenderOfAcceptedRideByLinkedPassengers;

	public void execute(Ride ride, String senderEmail) {
		for (Notification notification : ride.getNotifications()) {
			if (!notification.getStatus().equals(NotificationStatus.ACCEPTED)) {
				return;
			}
		}
		notifySenderOfAcceptedRideByLinkedPassengers.execute(senderEmail);

	}
}
