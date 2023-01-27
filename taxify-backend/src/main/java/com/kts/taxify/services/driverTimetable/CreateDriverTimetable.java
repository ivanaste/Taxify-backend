package com.kts.taxify.services.driverTimetable;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.DriverTimetable;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateDriverTimetable {

	private final SaveDriverTimetable saveTimetable;

	public void execute(Driver driver) {
		DriverTimetable driverTimetable = new DriverTimetable();
		driverTimetable.setDriver(driver);
		driverTimetable.setStartTime(LocalDateTime.now());
		saveTimetable.execute(driverTimetable);
	}
}
