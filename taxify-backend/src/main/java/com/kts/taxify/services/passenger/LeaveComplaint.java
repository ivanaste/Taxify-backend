package com.kts.taxify.services.passenger;

import com.kts.taxify.model.Complaint;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.ride.SaveRide;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveComplaint {
    private final GetSelf getSelf;
    private final RideRepository rideRepository;

    private final SaveRide saveRide;

    public void execute(String complaintReason) {
        Ride ride = rideRepository.getRideBySenderAndStatus(getSelf.execute().getEmail(), RideStatus.STARTED);
        Complaint complaint = Complaint.builder().complaintReason(complaintReason).ride(ride).build();
        ride.getComplaints().add(complaint);
        saveRide.execute(ride);
    }
}
