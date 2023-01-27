package com.kts.taxify.services.driverTimetable;

import com.kts.taxify.model.DriverTimetable;
import com.kts.taxify.repository.DriverTimetableRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateDriverTimetable {

	private final DriverTimetableRepository timetableRepository;

	private final SaveDriverTimetable saveTimetable;

	public boolean execute(String email) {
		Optional<DriverTimetable> timetable = timetableRepository.findDriverTimetableByDriver_EmailAndEndTimeIsNull(email);
		if (timetable.isPresent()) {
			DriverTimetable driverTimetable = timetable.get();
			driverTimetable.setEndTime(LocalDateTime.now());
			saveTimetable.execute(driverTimetable);
			return true;
		}
		return false;
	}
}
