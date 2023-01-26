package com.kts.taxify.services.driver;

import com.kts.taxify.services.driverTimetable.GetDriverRemainingWorkTime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyDriverOfActivityCheck {

	private final SimpMessagingTemplate simpMessagingTemplate;

	private final GetDriverRemainingWorkTime getDriverRemainingWorkTime;

	public void execute() {
		simpMessagingTemplate.convertAndSend("/topic/driver", "Remaining working hours check...");
	}
}
