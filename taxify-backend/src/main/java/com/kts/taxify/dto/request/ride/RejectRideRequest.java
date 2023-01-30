package com.kts.taxify.dto.request.ride;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RejectRideRequest {
    String rejectionReason;
}
