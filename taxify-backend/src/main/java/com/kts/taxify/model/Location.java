package com.kts.taxify.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {

    @Column(name = "longitude", nullable = true)
    Double longitude;

    @Column(name = "latitude", nullable = true)
    Double latitude;

}
