package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Waypoint extends BaseEntity {

    @Embedded
    Location location;

    Integer ordinalNumber;

    Boolean isStop;

    @ManyToMany(mappedBy = "waypoints")
    Set<Route> routes;

}
