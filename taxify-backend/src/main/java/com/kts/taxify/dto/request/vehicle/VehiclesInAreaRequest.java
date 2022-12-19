package com.kts.taxify.dto.request.vehicle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehiclesInAreaRequest {
    Double minLongitude;
    Double maxLongitude;
    Double minLatitude;
    Double maxLatitude;
}
