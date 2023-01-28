package com.kts.taxify.services.notification;

import com.kts.taxify.dto.request.notification.AddLinkedPassengersToTheRideRequest;
import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationStatus;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.services.passenger.NotifyRecipientsOfAddingToTheRide;
import com.kts.taxify.services.user.GetUserByEmail;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddLinkedPassengersToTheRide {

	private final GetUserByEmail getUserByEmail;

	private final NotifyRecipientsOfAddingToTheRide notifyRecipientsOfAddingToTheRide;

	private final SaveNotification saveNotification;

	public void execute(AddLinkedPassengersToTheRideRequest request) {
		Passenger sender = (Passenger) getUserByEmail.execute(request.getSenderEmail());
		for (String email : request.getRecipientsEmails()) {
			Passenger recipient = (Passenger) this.getUserByEmail.execute(email);
			final Notification notification = Notification.builder().sender(sender).type(NotificationType.ADDED_TO_THE_RIDE).recipient(recipient)
				.arrivalTime(LocalDateTime.now()).read(false).status(NotificationStatus.PENDING)
				.build();
			saveNotification.execute(notification);
			notifyRecipientsOfAddingToTheRide.execute(email);
		}
	}

}
