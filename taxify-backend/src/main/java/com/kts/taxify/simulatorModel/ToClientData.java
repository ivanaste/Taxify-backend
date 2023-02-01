package com.kts.taxify.simulatorModel;

import com.kts.taxify.model.Location;
import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class ToClientData {
    private String id;
    private Location start;
    private Location end;
}
