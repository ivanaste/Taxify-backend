package com.kts.taxify.repository;

import com.kts.taxify.model.DriverTimetable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverTimetableRepository extends JpaRepository<DriverTimetable, UUID> {

	Optional<DriverTimetable> findDriverTimetableByDriver_EmailAndEndTimeIsNull(String email);

	List<DriverTimetable> findAllByDriver_Email(String email);
}
