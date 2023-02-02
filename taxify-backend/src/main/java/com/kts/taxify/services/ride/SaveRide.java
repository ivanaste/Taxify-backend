package com.kts.taxify.services.ride;

import com.kts.taxify.model.Ride;
import com.kts.taxify.repository.RideRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveRide {
	private final RideRepository rideRepository;

	@Transactional
	public Ride execute(final Ride ride) {
		return rideRepository.save(ride);
	}
}
