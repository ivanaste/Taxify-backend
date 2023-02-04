package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.request.ride.RouteRequest;
import com.kts.taxify.dto.request.ride.WaypointRequest;
import com.kts.taxify.model.*;
import com.kts.taxify.repository.RideRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAcceptedRideForMultiplePassengersTest {

    @Mock
    private SaveRide saveRide;

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private CreateAcceptedRideForMultiplePassengers createAcceptedRideForMultiplePassengers;

    @Captor
    private ArgumentCaptor<Ride> rideArgumentCaptor;

    private static RouteRequest routeRequest;

    @BeforeAll
    private static void createRideRequest() {
        routeRequest = new RouteRequest();
        WaypointRequest waypoint = new WaypointRequest(19.83879, 45.23814, false, "Dr Ivana Ribara 5");
        routeRequest.setWaypoints(List.of(waypoint));
        routeRequest.setRouteDistance(100.00);
    }


    @Test
    @DisplayName("Should create accepted ride for multiple passengers")
    public void shouldCreateAcceptedRideForOnePassenger() {
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        rideRequest.setRouteRequest(routeRequest);
        Passenger passenger = Passenger.builder().email("test@gmail.com").build();
        Driver driver = Driver.builder().vehicle(Vehicle.builder().type(VehicleType.HATCHBACK).build()).build();

        when(rideRepository.getRideBySenderAndStatus(passenger.getEmail(), RideStatus.PENDING)).thenReturn(new Ride());

        createAcceptedRideForMultiplePassengers.execute(rideRequest, driver, passenger);
        verify(saveRide, times(1)).execute(rideArgumentCaptor.capture());
        assertThat(rideArgumentCaptor.getValue().getStatus()).isEqualTo(RideStatus.ACCEPTED);
        assertThat(rideArgumentCaptor.getValue().getDriver()).isEqualTo(driver);
    }
}
