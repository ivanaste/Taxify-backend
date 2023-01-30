package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {

	@Column(name = "longitude", nullable = true)
	Double longitude;

	@Column(name = "latitude", nullable = true)
	Double latitude;

	public Double distanceTo(Location l) {
		Double x = Math.pow(Math.abs(l.getLatitude() - latitude), 2);
		Double y = Math.pow(Math.abs(l.getLongitude() - longitude), 2);
		return Math.sqrt(x + y);
	}
}
