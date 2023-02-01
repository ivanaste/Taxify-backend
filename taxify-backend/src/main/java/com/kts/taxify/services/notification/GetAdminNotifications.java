package com.kts.taxify.services.notification;

import com.kts.taxify.converter.NotificationConverter;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAdminNotifications {
    private final NotificationRepository notificationRepository;

    public Collection<NotificationResponse> execute() {

        List<NotificationResponse> adminNotifications = new ArrayList<>();
        notificationRepository.findAllByRecipientIsNullAndReadIsFalse().forEach(notification -> {
            adminNotifications.add(NotificationConverter.toNotificationResponse(notification));
        });
        adminNotifications.sort((not1, not2) -> not2.getArrivalTime().compareTo(not1.getArrivalTime()));
        return adminNotifications;
    }
}
