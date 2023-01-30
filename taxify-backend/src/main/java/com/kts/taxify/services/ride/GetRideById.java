package com.kts.taxify.services.ride;

import com.kts.taxify.exception.RideNotFoundException;
import com.kts.taxify.exception.RoleNotFoundException;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.Role;
import com.kts.taxify.repository.RideRepository;
import com.kts.taxify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetRideById {

    private final RideRepository rideRepository;

    @Transactional(readOnly = true)
    public Ride execute(final UUID id) {
        return rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
    }
}
