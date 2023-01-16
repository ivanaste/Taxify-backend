package com.kts.taxify.services.driverTimetable;

import com.kts.taxify.model.DriverTimetable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetRemainingWorkTime {
	private final GetDriverTimetablesToday getTimetablesToday;

	static final long ALLOWED_NUMBER_OF_WORKING_MINUTES = 480;

	public long execute(String email) {
		List<DriverTimetable> driverTimetables = getTimetablesToday.execute(email);
		long workedMinutes = 0;
		for (DriverTimetable timetable : driverTimetables) {
			if (timetable.getEndTime() == null) {
				workedMinutes += ChronoUnit.MINUTES.between(timetable.getStartTime(), LocalDateTime.now());
			} else {
				workedMinutes += ChronoUnit.MINUTES.between(timetable.getStartTime(), timetable.getEndTime());
			}
		}
		return ALLOWED_NUMBER_OF_WORKING_MINUTES - workedMinutes;
	}
}
