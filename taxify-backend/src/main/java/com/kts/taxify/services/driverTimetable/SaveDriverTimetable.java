package com.kts.taxify.services.driverTimetable;

import com.kts.taxify.model.DriverTimetable;
import com.kts.taxify.repository.DriverTimetableRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveDriverTimetable {

	private final DriverTimetableRepository driverTimetableRepository;

	@Transactional(readOnly = false)
	public DriverTimetable execute(final DriverTimetable timetable) {
		return driverTimetableRepository.save(timetable);
	}
}
