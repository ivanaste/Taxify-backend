package com.kts.taxify.model;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Waypoint extends BaseEntity {

	@Embedded
	Location location;

	Integer ordinalNumber;

	Boolean isStop;

	@ManyToMany(mappedBy = "waypoints")
	Set<Route> routes;

}
