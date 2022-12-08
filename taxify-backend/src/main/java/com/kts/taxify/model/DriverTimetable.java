package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "driver_timetable")
public class DriverTimetable extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "driver_id")
    Driver driver;

    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @Column(name = "end_time")
    LocalDateTime endTime;
}
