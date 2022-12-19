package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
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
