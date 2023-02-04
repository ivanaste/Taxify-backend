package com.kts.taxify.services.ride;

import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.request.ride.RouteRequest;
import com.kts.taxify.dto.request.ride.WaypointRequest;
import com.kts.taxify.model.*;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateAcceptedRideForOnePassengerTest {

    @Mock
    private SaveRide saveRide;

    @InjectMocks
    private CreateAcceptedRideForOnePassenger createAcceptedRideForOnePassenger;

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

        createAcceptedRideForOnePassenger.execute(rideRequest, Driver.builder().vehicle(Vehicle.builder().type(VehicleType.HATCHBACK).build()).build(), new Passenger());
        verify(saveRide, times(1)).execute(rideArgumentCaptor.capture());
        assertThat(rideArgumentCaptor.getValue().getStatus()).isEqualTo(RideStatus.ACCEPTED);
    }

}
