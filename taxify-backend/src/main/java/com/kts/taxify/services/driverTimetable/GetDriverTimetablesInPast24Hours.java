package com.kts.taxify.services.driverTimetable;

import com.kts.taxify.model.DriverTimetable;
import com.kts.taxify.repository.DriverTimetableRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetDriverTimetablesInPast24Hours {

	private final DriverTimetableRepository driverTimetableRepository;

	public List<DriverTimetable> execute(String driverEmail) {
		List<DriverTimetable> driverTimetables = driverTimetableRepository.findAllByDriver_Email(driverEmail);
		return driverTimetables.stream().filter(timetable -> timetable.getStartTime().isAfter(LocalDateTime.now().minusDays(1)))
			.collect(Collectors.toList());
	}
}
