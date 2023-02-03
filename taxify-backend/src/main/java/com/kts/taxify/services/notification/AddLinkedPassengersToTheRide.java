package com.kts.taxify.services.notification;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.services.passenger.GetPassengersByEmails;
import com.kts.taxify.services.passenger.NotifyRecipientsOfAddingToTheRide;
import com.kts.taxify.services.ride.SaveRide;
import com.kts.taxify.services.user.GetUserByEmail;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddLinkedPassengersToTheRide {

	private final GetUserByEmail getUserByEmail;

	private final NotifyRecipientsOfAddingToTheRide notifyRecipientsOfAddingToTheRide;

	private final SaveRide saveRide;

	private final GetPassengersByEmails getPassengersByEmails;

	public void execute(LinkedPassengersToTheRideRequest request) {
		Set<Passenger> recipients = getPassengersByEmails.execute(request.getRecipientsEmails());
		Passenger sender = (Passenger) getUserByEmail.execute(request.getSenderEmail());
		recipients.add(sender);
		Ride ride = Ride.builder().status(RideStatus.PENDING).passengers(recipients).sender(request.getSenderEmail()).build();
		saveRide.execute(ride);
		notifyRecipientsOfAddingToTheRide.execute(recipients, sender, ride);
	}

}
