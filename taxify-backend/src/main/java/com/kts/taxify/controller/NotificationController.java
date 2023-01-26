package com.kts.taxify.controller;

import com.kts.taxify.dto.request.notification.AddLinkedPassengersToTheRideRequest;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.notification.AddLinkedPassengersToTheRide;
import com.kts.taxify.services.notification.GetPassengerNotifications;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final AddLinkedPassengersToTheRide addLinkedPassengersToTheRide;

	private final GetPassengerNotifications getPassengerNotifications;

	@PostMapping("/addToTheRide")
	public void addPassengersToTheRide(@Valid @RequestBody final AddLinkedPassengersToTheRideRequest addLinkedPassengersToTheRideRequest) {
		addLinkedPassengersToTheRide.execute(addLinkedPassengersToTheRideRequest);
	}

	@HasAnyPermission({ Permission.GET_ALL_NOTIFICATIONS })
	@GetMapping("/all")
	public Collection<NotificationResponse> getAllPassengerNotifications() {
		return getPassengerNotifications.execute();
	}
}
