package com.kts.taxify.dto.request.ride;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WaypointRequest {

	Double longitude;

	Double latitude;

	boolean stop;
}
