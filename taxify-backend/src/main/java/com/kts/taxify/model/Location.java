package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {

	@Column(name = "longitude", nullable = false)
	Double longitude;

	@Column(name = "latitude", nullable = false)
	Double latitude;

}
