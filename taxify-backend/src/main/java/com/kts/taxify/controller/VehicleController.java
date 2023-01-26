package com.kts.taxify.controller;

import com.kts.taxify.dto.request.vehicle.ChangeVehicleLocationRequest;
import com.kts.taxify.dto.response.VehicleResponse;
import com.kts.taxify.model.VehicleType;
import com.kts.taxify.services.vehicle.ChangeVehicleLocation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

	private final ChangeVehicleLocation changeVehicleLocation;

	@PutMapping("/location")
	public VehicleResponse changeVehicleLocation(@RequestBody final ChangeVehicleLocationRequest changeVehicleLocationRequest) {
		return changeVehicleLocation.execute(changeVehicleLocationRequest);
	}

	@GetMapping("/vehicleTypes")
	public ResponseEntity<List<String>> getVehicleTypes() {
		return new ResponseEntity<>(Arrays.stream(VehicleType.values()).map(VehicleType::getName).collect(Collectors.toList()), HttpStatus.OK);
	}
}
