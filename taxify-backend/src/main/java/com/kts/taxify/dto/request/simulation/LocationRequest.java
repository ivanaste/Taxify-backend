package com.kts.taxify.dto.request.simulation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class LocationRequest {

    Double longitude;

    @Column(name = "latitude", nullable = true)
    Double latitude;

    boolean stop;
}
