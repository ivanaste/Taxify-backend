package com.kts.taxify.services.reports;

import com.kts.taxify.model.Charge;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.ride.GetRideHistoryForPeriod;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetMoneyPerDayInPeriod {
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final GetRideHistoryForPeriod getRideHistoryForPeriod;

    public Map<LocalDate, Double> execute(LocalDate initDate, LocalDate termDate) {
        User self = getUserByEmail.execute(getSelf.execute().getEmail());
        List<Ride> rideHistoryForPeriod = getRideHistoryForPeriod.execute(initDate, termDate);
        Map<LocalDate, Double> result = initiateMap(initDate, termDate);
        for (Ride ride : rideHistoryForPeriod) {
            LocalDate rideTime = ride.getFinishedAt().toLocalDate();
            if (result.containsKey(rideTime)) {
                if (self.getRole().getName().equals("DRIVER")) {
                    result.put(rideTime, result.get(rideTime) + ride.getRoute().getPrice());
                } else if (self.getRole().getName().equals("PASSENGER")) {
                    for (Charge charge : ride.getPassengersCharges()) {
                        if (charge.getCustomerId().equals(((Passenger) self).getCustomerId())) {
                            result.put(rideTime, result.get(rideTime) + charge.getAmount());
                        }
                    }
                }
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
