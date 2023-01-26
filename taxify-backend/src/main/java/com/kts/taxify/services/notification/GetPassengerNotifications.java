package com.kts.taxify.services.notification;

import com.kts.taxify.converter.NotificationConverter;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.repository.NotificationRepository;
import com.kts.taxify.services.auth.GetSelf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPassengerNotifications {
	private final NotificationRepository notificationRepository;

	private final GetSelf getSelf;

	public Collection<NotificationResponse> execute() {
		List<NotificationResponse> passengerNotifications = new ArrayList<>();
		notificationRepository.findAll().forEach(notification -> {
			if (notification.getRecipients().stream().map(recipient -> recipient.getEmail()).collect(Collectors.toList())
				.contains(getSelf.execute().getEmail())) {
				passengerNotifications.add(NotificationConverter.toNotificationResponse(notification));
			}
		});
		Collections.sort(passengerNotifications, (not1, not2) -> not2.getArrivalTime().compareTo(not1.getArrivalTime()));
		return passengerNotifications;
	}
}
