package com.kts.taxify.dto.request.vehicle;

import com.kts.taxify.model.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeVehicleLocationRequest {
    UUID id;
    Location location;
}