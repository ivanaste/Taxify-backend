package com.kts.taxify.services.notification;

import com.kts.taxify.converter.NotificationConverter;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.Notification;
import com.kts.taxify.model.NotificationStatus;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcceptAddingToTheRide {

	private final GetNotificationById getNotificationById;

	private final SaveNotification saveNotification;

	private final CheckIfAllRideNotificationsAreAccepted checkIfAllRideNotificationsAreAccepted;

	public NotificationResponse execute(UUID notificationId) {
		Notification notification = getNotificationById.execute(notificationId);
		notification.setStatus(NotificationStatus.ACCEPTED);
		saveNotification.execute(notification);

		checkIfAllRideNotificationsAreAccepted.execute(notification.getRide(), notification.getSender().getEmail());
		return NotificationConverter.toNotificationResponse(notification);
	}
}
