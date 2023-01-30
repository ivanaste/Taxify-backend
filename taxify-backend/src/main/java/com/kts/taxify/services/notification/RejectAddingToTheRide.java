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
public class RejectAddingToTheRide {

	private final GetNotificationById getNotificationById;

	private final SaveNotification saveNotification;

	public NotificationResponse execute(UUID notificationId) {
		Notification notification = getNotificationById.execute(notificationId);
		notification.setStatus(NotificationStatus.REJECTED);
		saveNotification.execute(notification);
		return NotificationConverter.toNotificationResponse(notification);
	}
}
