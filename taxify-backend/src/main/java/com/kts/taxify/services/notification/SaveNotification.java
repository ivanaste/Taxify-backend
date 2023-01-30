package com.kts.taxify.services.notification;

import com.kts.taxify.model.Notification;
import com.kts.taxify.repository.NotificationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveNotification {
	private final NotificationRepository notificationRepository;

	@Transactional(readOnly = false)
	public Notification execute(final Notification notification) {
		return notificationRepository.save(notification);
	}
}
