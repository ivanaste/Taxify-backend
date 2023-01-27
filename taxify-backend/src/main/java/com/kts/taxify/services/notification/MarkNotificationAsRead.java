package com.kts.taxify.services.notification;

import com.kts.taxify.model.Notification;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarkNotificationAsRead {

	private final SaveNotification saveNotification;

	public void execute(Notification notification) {
		notification.setRead(true);
		saveNotification.execute(notification);
	}
}
