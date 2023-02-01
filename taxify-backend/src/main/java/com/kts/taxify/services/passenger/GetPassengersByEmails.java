package com.kts.taxify.services.passenger;

import com.kts.taxify.model.Passenger;
import com.kts.taxify.services.user.GetUserByEmail;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPassengersByEmails {

	private final GetUserByEmail getUserByEmail;

	public Set<Passenger> execute(Set<String> passengersEmails) {
		Set<Passenger> passengers = new HashSet<>();
		for (String email : passengersEmails) {
			Passenger passenger = (Passenger) this.getUserByEmail.execute(email);
			passengers.add(passenger);
		}
		return passengers;
	}
}
