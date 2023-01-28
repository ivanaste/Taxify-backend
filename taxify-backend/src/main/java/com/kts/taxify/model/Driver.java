package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "driver")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Driver extends User {
    @OneToOne(cascade = CascadeType.ALL)
    Vehicle vehicle;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    Set<DriverTimetable> timetables;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    Set<Ride> ride;

    @Column(name = "active")
    private boolean active;
}
