package com.kts.taxify.services.ride;

import com.kts.taxify.model.Ride;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRideHistoryForPeriod {
    private final GetRideHistory getRideHistory;

    public List<Ride> execute(LocalDate initDate, LocalDate termDate) {
        List<Ride> rideHistory = getRideHistory.execute();
        return rideHistory.stream().filter(ride -> ride.getFinishedAt().isBefore(ChronoLocalDateTime.from(termDate)) && ride.getFinishedAt().isAfter(ChronoLocalDateTime.from(initDate))).toList();
    }
}
