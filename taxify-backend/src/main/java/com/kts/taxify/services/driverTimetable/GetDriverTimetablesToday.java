package com.kts.taxify.services.driverTimetable;

import com.kts.taxify.model.DriverTimetable;
import com.kts.taxify.repository.DriverTimetableRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetDriverTimetablesToday {

	private final DriverTimetableRepository driverTimetableRepository;

	public List<DriverTimetable> execute(String driverEmail) {
		List<DriverTimetable> driverTimetables = driverTimetableRepository.findAllByDriver_Email(driverEmail);
		return driverTimetables.stream().filter(timetable -> timetable.getStartTime().toLocalDate().isEqual(LocalDate.now())).collect(Collectors.toList());
	}
}
