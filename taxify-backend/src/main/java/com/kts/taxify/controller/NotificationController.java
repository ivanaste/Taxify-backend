package com.kts.taxify.controller;

import com.kts.taxify.dto.request.notification.AddLinkedPassengersToTheRideRequest;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.Permission;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.notification.AcceptAddingToTheRide;
import com.kts.taxify.services.notification.AddLinkedPassengersToTheRide;
import com.kts.taxify.services.notification.GetPassengerNotifications;
import com.kts.taxify.services.notification.RejectAddingToTheRide;

import java.util.Collection;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	private final AcceptAddingToTheRide acceptAddingToTheRide;

	private final RejectAddingToTheRide rejectAddingToTheRide;

	@PostMapping("/addToTheRide")
	public void addPassengersToTheRide(@Valid @RequestBody final AddLinkedPassengersToTheRideRequest addLinkedPassengersToTheRideRequest) {
		addLinkedPassengersToTheRide.execute(addLinkedPassengersToTheRideRequest);
	}

	@HasAnyPermission({ Permission.GET_ALL_NOTIFICATIONS })
	@GetMapping("/all/{notificationsAreRead}")
	public Collection<NotificationResponse> getAllPassengerNotifications(@PathVariable("notificationsAreRead") final boolean notificationsAreRead) {
		return getPassengerNotifications.execute(notificationsAreRead);
	}

	@HasAnyPermission({ Permission.ANSWER_ON_ADDING_TO_THE_RIDE })
	@PutMapping("/acceptAddingToTheRide/{notificationId}")
	public NotificationResponse acceptAddingToTheRide(@PathVariable("notificationId") final UUID notificationId) {
		return acceptAddingToTheRide.execute(notificationId);
	}

	@HasAnyPermission({ Permission.ANSWER_ON_ADDING_TO_THE_RIDE })
	@PutMapping("/rejectAddingToTheRide/{notificationId}")
	public NotificationResponse rejectAddingToTheRide(@PathVariable("notificationId") final UUID notificationId) {
		return rejectAddingToTheRide.execute(notificationId);
	}
}
