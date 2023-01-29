package com.kts.taxify.services.passenger;

import com.kts.taxify.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyPassengerOfChangedRideState {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void execute(String passengerEmail, NotificationType notificationType) {

            simpMessagingTemplate.convertAndSend("/topic/passenger-notification/" + passengerEmail,
                    notificationType.toString());
    }
}
