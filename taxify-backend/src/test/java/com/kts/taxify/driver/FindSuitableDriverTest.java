package com.kts.taxify.driver;

import com.kts.taxify.dto.request.notification.LinkedPassengersToTheRideRequest;
import com.kts.taxify.dto.request.ride.RequestedRideRequest;
import com.kts.taxify.dto.response.DriverResponse;
import com.kts.taxify.exception.InvalidPaymentForSharedRideException;
import com.kts.taxify.exception.RideInProcessException;
import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Location;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.Vehicle;
import com.kts.taxify.repository.DriverRepository;
import com.kts.taxify.services.checkout.CheckoutRide;
import com.kts.taxify.services.driver.FindSuitableDriver;
import com.kts.taxify.services.driver.NotifyDriver;
import com.kts.taxify.services.driver.SetDriverVehicleAssOccupied;
import com.kts.taxify.services.passenger.NotifyPassengerOfChangedRideState;
import com.kts.taxify.services.ride.CreateAcceptedRide;
import com.kts.taxify.services.ride.GetAssignedRide;
import com.kts.taxify.services.simulations.GetClosestUnoccupiedDriver;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindSuitableDriverTest {
    @Mock
    private GetClosestUnoccupiedDriver getClosestUnoccupiedDriver;

    @Mock
    private NotifyDriver notifyDriver;

    @Mock
    private CreateAcceptedRide createAcceptedRide;

    @Mock
    private CheckoutRide checkoutRide;

    @Mock
    private SetDriverVehicleAssOccupied setDriverVehicleAssOccupied;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private GetAssignedRide getAssignedRide;

    @Mock
    private NotifyPassengerOfChangedRideState notifyPassengerOfChangedRideState;



    @InjectMocks
    private FindSuitableDriver findSuitableDriver;

    @Test
    @DisplayName("Should find suitable driver for ride with valid payment")
    public void shouldReturnSuitableDriverForRideWithPayment() throws StripeException, IOException, ExecutionException, InterruptedException {
        Location location = new Location();
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        LinkedPassengersToTheRideRequest linkedPassengersToTheRideRequest = LinkedPassengersToTheRideRequest.builder().senderEmail("test@gmail.com").build();
        rideRequest.setClientLocation(location);
        rideRequest.setPassengers(linkedPassengersToTheRideRequest);

        Vehicle vehicle = new Vehicle();
        Driver driver = Driver.builder().email("test@gmail.com").vehicle(vehicle).build();
        Ride assignedRide = new Ride();

        when(getAssignedRide.execute()).thenReturn(null);
        when(getClosestUnoccupiedDriver.execute(location)).thenReturn(driver);
        when(createAcceptedRide.execute(rideRequest, driver)).thenReturn(assignedRide);
        when(checkoutRide.execute(assignedRide)).thenReturn(true);

        DriverResponse driverResponse = findSuitableDriver.execute(rideRequest);
        assertEquals("test@gmail.com", driverResponse.getEmail());
    }

    @Test
    @DisplayName("Should throw error drive already in process")
    public void shouldThrowErrorDriveAlreadyInProcess() {
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        Ride ride = new Ride();
        when(getAssignedRide.execute()).thenReturn(ride);
        RideInProcessException exception = assertThrows(RideInProcessException.class, () -> findSuitableDriver.execute(rideRequest));
        assertEquals("ride_in_process", exception.getKey().getCode());
    }

    @Test
    @DisplayName("Should throw error for invalid payment")
    public void shouldReturnSuitableDriverForRideWithoutPayment() throws StripeException, IOException, ExecutionException, InterruptedException {
        Location location = new Location();
        RequestedRideRequest rideRequest = new RequestedRideRequest();
        rideRequest.setClientLocation(location);

        Vehicle vehicle = new Vehicle();
        Driver driver = Driver.builder().email("test@gmail.com").vehicle(vehicle).build();
        Ride assignedRide = new Ride();

        when(getClosestUnoccupiedDriver.execute(location)).thenReturn(driver);
        when(createAcceptedRide.execute(rideRequest, driver)).thenReturn(assignedRide);
        when(checkoutRide.execute(assignedRide)).thenReturn(false);

        InvalidPaymentForSharedRideException exception = assertThrows(InvalidPaymentForSharedRideException.class, () -> findSuitableDriver.execute(rideRequest));
        assertEquals("invalid_payment_for_shared_ride", exception.getKey().getCode());

    }
}
