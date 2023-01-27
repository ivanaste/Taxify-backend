package com.kts.taxify.services.notification;

import com.kts.taxify.converter.NotificationConverter;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.User;
import com.kts.taxify.repository.NotificationRepository;
import com.kts.taxify.services.auth.GetSelf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPassengerNotifications {
	private final NotificationRepository notificationRepository;

	private final GetSelf getSelf;

	private final MarkNotificationAsRead markNotificationAsRead;

	public Collection<NotificationResponse> execute(boolean notificationsAreRead) {

		List<NotificationResponse> passengerNotifications = new ArrayList<>();
		notificationRepository.findAll().forEach(notification -> {
			List<String> recipientsEmails = notification.getRecipients().stream().map(User::getEmail).toList();
			if (recipientsEmails.contains(getSelf.execute().getEmail())) {
				if (notificationsAreRead) {
					markNotificationAsRead.execute(notification);
				}
				passengerNotifications.add(NotificationConverter.toNotificationResponse(notification));
			}

		});
		passengerNotifications.sort((not1, not2) -> not2.getArrivalTime().compareTo(not1.getArrivalTime()));
		return passengerNotifications;
	}
}
