package com.kts.taxify.converter;

import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.Notification;

import org.modelmapper.ModelMapper;

public class NotificationConverter {

	private final static ModelMapper modelMapper = new ModelMapper();

	public static NotificationResponse toNotificationResponse(final Notification notification) {
		NotificationResponse notificationResponse = modelMapper.map(notification, NotificationResponse.class);
		return notificationResponse;
	}
}
