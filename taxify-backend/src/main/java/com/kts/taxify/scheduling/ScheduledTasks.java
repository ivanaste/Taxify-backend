package com.kts.taxify.scheduling;

import com.kts.taxify.services.driver.NotifyDriverOfActivityCheck;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
	private final NotifyDriverOfActivityCheck notifyDriverOfActivityCheck;

	@Scheduled(cron = "0 * * * * *")
	public void notifyDriverOfActivityCheck() {
		System.out.println("Minute passed...");
		notifyDriverOfActivityCheck.execute();
	}
}
