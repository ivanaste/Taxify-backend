package com.kts.taxify.services.notification;

import com.kts.taxify.dto.request.notification.AddLinkedPassengersToTheRideRequest;
import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.repository.NotificationRepository;
import com.kts.taxify.services.passenger.NotifyRecipientsOfAddingToTheRide;
import com.kts.taxify.services.user.GetUserByEmail;
import com.kts.taxify.services.vehicle.NotifyFrontendOfVehicleLocationChange;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddLinkedPassengersToTheRide {

	private final NotificationRepository notificationRepository;

	private final GetUserByEmail getUserByEmail;

	private final NotifyRecipientsOfAddingToTheRide notifyRecipientsOfAddingToTheRide;

	private final NotifyFrontendOfVehicleLocationChange notifyFrontendOfVehicleLocationChange;

	public void execute(AddLinkedPassengersToTheRideRequest request) {
		Passenger sender = (Passenger) getUserByEmail.execute(request.getSenderEmail());
		Set<Passenger> recipients = new HashSet<>();
		for (String email : request.getRecipientsEmails()) {
			recipients.add((Passenger) this.getUserByEmail.execute(email));
		}
		final Notification notification = Notification.builder().sender(sender).type(NotificationType.ADDED_TO_THE_RIDE).recipients(recipients)
			.arrivalTime(LocalDateTime.now()).read(false)
			.build();
		notificationRepository.save(notification);
		notifyRecipientsOfAddingToTheRide.execute(request.getRecipientsEmails());
	}

}
