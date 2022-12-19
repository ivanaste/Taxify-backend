package com.kts.taxify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kts.taxify.editor.LocationEditor;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Parking;
import com.kts.taxify.services.parking.CreateParking;
import com.kts.taxify.services.parking.DeleteParking;
import com.kts.taxify.services.parking.GetClosestParking;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final GetClosestParking getClosestParking;

    private final CreateParking createParking;

    private final DeleteParking deleteParking;

    private final ObjectMapper objectMapper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Location.class, new LocationEditor(objectMapper));
    }

    @GetMapping("/closest")
    public Parking getClosestParking(@Valid @RequestParam("location") final Location location) {
        return getClosestParking.execute(location);
    }

    @PostMapping("/create")
    public Parking createParking(@Valid @RequestBody final Location location) {
        return createParking.execute(location);
    }

    @DeleteMapping("/delete")
    public void deleteParking(@Valid @RequestBody UUID id) {
        deleteParking.execute(id);
    }
}
