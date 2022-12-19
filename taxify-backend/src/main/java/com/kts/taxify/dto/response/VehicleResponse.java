package com.kts.taxify.dto.response;

import com.kts.taxify.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    @NotEmpty
    UUID id;

    @NotEmpty
    private Location location;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotEmpty
    private String horsePower;
}
