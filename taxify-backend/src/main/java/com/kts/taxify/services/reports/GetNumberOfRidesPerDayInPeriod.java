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
public class GetNumberOfRidesPerDayInPeriod {
    private final GetRideHistoryForPeriod getRideHistoryForPeriod;

    public Map<LocalDate, Integer> execute(LocalDate initDate, LocalDate termDate) {
        List<Ride> rideHistoryForPeriod = getRideHistoryForPeriod.execute(initDate, termDate);
        Map<LocalDate, Integer> result = initiateMap(initDate, termDate);
        for (Ride ride : rideHistoryForPeriod) {
            LocalDate rideTime = ride.getFinishedAt().toLocalDate();
            if (result.containsKey(rideTime)) {
                result.put(rideTime, result.get(rideTime) + 1);
            }
        }
        return result;
    }

    private Map<LocalDate, Integer> initiateMap(LocalDate initDate, LocalDate termDate) {
        Map<LocalDate, Integer> result = new HashMap<>();
        LocalDate temp = initDate;
        while (temp.isBefore(termDate) || temp.isEqual(termDate)) {
            result.put(temp, 0);
            temp = temp.plusDays(1);
        }
        return result;
    }
}
