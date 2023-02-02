package com.kts.taxify.controller;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.dto.response.NotificationResponse;
import com.kts.taxify.model.NotificationType;
import com.kts.taxify.model.Permission;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.security.HasAnyPermission;
import com.kts.taxify.services.notification.*;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.ride.ChangeRideStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final AddLinkedPassengersToTheRide addLinkedPassengersToTheRide;

    private final GetPassengerNotifications getPassengerNotifications;

    private final GetAdminNotifications getAdminNotifications;

    private final AcceptAddingToTheRide acceptAddingToTheRide;

    private final RejectAddingToTheRide rejectAddingToTheRide;

    private final NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;

    private final ChangeRideStatus changeRideStatus;

    @HasAnyPermission({Permission.LINK_PASSENGERS_TO_THE_RIDE})
    @PostMapping("/addToTheRide")
    public void addPassengersToTheRide(@Valid @RequestBody LinkedPassengersToTheRideRequest linkedPassengersToTheRideRequest) {
        addLinkedPassengersToTheRide.execute(linkedPassengersToTheRideRequest);
    }

    @GetMapping("/all/{notificationsAreRead}")
    public Collection<NotificationResponse> getAllPassengerNotifications(@PathVariable("notificationsAreRead") boolean notificationsAreRead) {
        return getPassengerNotifications.execute(notificationsAreRead);
    }

    @HasAnyPermission({Permission.GET_ALL_ADMINS_NOTIFICATIONS})
    @GetMapping("/all")
    public Collection<NotificationResponse> getAllAdminNotifications() {
        return getAdminNotifications.execute();
    }

    @HasAnyPermission({Permission.ANSWER_ON_ADDING_TO_THE_RIDE})
    @PutMapping("/acceptAddingToTheRide/{notificationId}/{paymentMethodId}")
    public NotificationResponse acceptAddingToTheRide(@PathVariable("notificationId") UUID notificationId, @PathVariable("paymentMethodId") String paymentMethodId) {
        return acceptAddingToTheRide.execute(notificationId, paymentMethodId);
    }

    @HasAnyPermission({Permission.ANSWER_ON_ADDING_TO_THE_RIDE})
    @PutMapping("/rejectAddingToTheRide/{notificationId}")
    public NotificationResponse rejectAddingToTheRide(@PathVariable("notificationId") UUID notificationId) {
        return rejectAddingToTheRide.execute(notificationId);
    }

    @PutMapping("/vehicleArrivedToClient")
    @HasAnyPermission({Permission.RIDE_STATUS_CHANGED})
    public void notifyOfVehicleArrivedToClient() {
        changeRideStatus.execute(RideStatus.ARRIVED, NotificationType.VEHICLE_ARRIVED);
    }
}
