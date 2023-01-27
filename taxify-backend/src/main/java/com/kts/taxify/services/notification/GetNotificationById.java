package com.kts.taxify.services.notification;

import com.kts.taxify.exception.NotificationNotFound;
import com.kts.taxify.model.Notification;
import com.kts.taxify.repository.NotificationRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetNotificationById {

	private final NotificationRepository notificationRepository;

	@Transactional(readOnly = true)
	public Notification execute(final UUID id) {
		return notificationRepository.findById(id).orElseThrow(NotificationNotFound::new);
	}
}
