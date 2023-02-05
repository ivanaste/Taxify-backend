package com.kts.taxify.services.reports;

import com.kts.taxify.model.Ride;
import com.kts.taxify.services.ride.GetRideHistoryForPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetNumberOfKilometersPerDayInPeriod {
    private final GetRideHistoryForPeriod getRideHistoryForPeriod;

    public Map<LocalDate, Double> execute(LocalDate initDate, LocalDate termDate) {
        List<Ride> rideHistoryForPeriod = getRideHistoryForPeriod.execute(initDate, termDate);
        Map<LocalDate, Double> result = initiateMap(initDate, termDate);
        for (Ride ride : rideHistoryForPeriod) {
            LocalDate rideTime = ride.getFinishedAt().toLocalDate();
            if (result.containsKey(rideTime)) {
                result.put(rideTime, result.get(rideTime) + ride.getRoute().getDistance());
            }
        }
        return result;
    }

    private Map<LocalDate, Double> initiateMap(LocalDate initDate, LocalDate termDate) {
        Map<LocalDate, Double> result = new HashMap<>();
        LocalDate temp = initDate;
        while (temp.isBefore(termDate) || temp.isEqual(termDate)) {
            result.put(temp, (double) 0);
            temp = temp.plusDays(1);
        }
        return result;
    }
}
