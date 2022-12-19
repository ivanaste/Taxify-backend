package com.kts.taxify.services.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyFrontendOfVehicleLocationChange {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void execute() {
        simpMessagingTemplate.convertAndSend("/topic/vehicles", "Vehicles Changed...");
    }
}
