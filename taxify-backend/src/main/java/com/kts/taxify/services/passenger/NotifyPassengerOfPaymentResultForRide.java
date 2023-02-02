package com.kts.taxify.services.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyPassengerOfPaymentResultForRide {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void execute(String passengerEmail, String message) {
        simpMessagingTemplate.convertAndSend("/topic/payment/" + passengerEmail, message);
    }
}
