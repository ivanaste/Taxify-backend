package com.kts.taxify.repository;

import com.kts.taxify.model.Driver;
import com.kts.taxify.model.Passenger;
import com.kts.taxify.model.Ride;
import com.kts.taxify.model.RideStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
public class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void shouldSaveRide() {
        Ride testRide = Ride.builder().sender("test@gmail.com").status(RideStatus.ACCEPTED).build();

        Ride actualRide = rideRepository.save(testRide);

        assertThat(actualRide).usingRecursiveComparison().ignoringFields("id").isEqualTo(testRide);
    }

    @Test
    public void shouldReturnRideByDriverAndStatusAccepted() {
        Driver driver = Driver.builder().passwordHash("hash").email("brzi@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride = Ride.builder().driver(driver).sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(driver);
        testEntityManager.persist(ride);
        testEntityManager.flush();

        Ride actualRide = rideRepository.getRideByDriverAndStatus(driver, RideStatus.ACCEPTED);
        assertEquals("brzi@gmail.com", actualRide.getDriver().getEmail());
        assertEquals(RideStatus.ACCEPTED, actualRide.getStatus());
    }

    @Test
    public void shouldReturnNullByDriverAndStatusAcceptedBecauseRideIsFinished() {
        Driver driver = Driver.builder().passwordHash("hash").email("brzi@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride = Ride.builder().driver(driver).sender("milic@gmail.com").status(RideStatus.FINISHED).build();

        testEntityManager.persist(driver);
        testEntityManager.persist(ride);
        testEntityManager.flush();

        Ride actualRide = rideRepository.getRideByDriverAndStatus(driver, RideStatus.ACCEPTED);
        assertEquals(null, actualRide);
    }

    @Test
    public void shouldReturnNullByDriverAndStatusBecauseDriverDoesntHaveRide() {
        Driver driver = Driver.builder().passwordHash("hash").email("brzi@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride = Ride.builder().sender("milic@gmail.com").status(RideStatus.FINISHED).build();

        testEntityManager.persist(driver);
        testEntityManager.persist(ride);
        testEntityManager.flush();

        Ride actualRide = rideRepository.getRideByDriverAndStatus(driver, RideStatus.FINISHED);
        assertNull(actualRide);
    }

    @Test
    public void shouldReturnRideWithPassengerAndStatusAccepted() {
        Passenger passenger = Passenger.builder().passwordHash("hash").email("putnik@gmail.com").name("Marko").surname("Markovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride = Ride.builder().passengers(new HashSet<>(Collections.singletonList(passenger))).sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(passenger);
        testEntityManager.persist(ride);
        testEntityManager.flush();

        Ride actualRide = rideRepository.getRideByPassengersContainingAndStatusIn(passenger, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ON_DESTINATION));
        assertEquals(ride, actualRide);
    }

    @Test
    public void shouldReturnFirstAddedRideByDriverAndStatus() {
        Driver driver = Driver.builder().passwordHash("hash").email("brzi@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride1 = Ride.builder().driver(driver).sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();
        Ride ride2 = Ride.builder().driver(driver).sender("milic@gmail.com").status(RideStatus.ON_DESTINATION).build();

        testEntityManager.persist(driver);
        testEntityManager.persist(ride1);
        testEntityManager.persist(ride2);
        testEntityManager.flush();

        Ride actualRide = rideRepository.findFirstByDriverAndStatusIn(driver, Arrays.asList(RideStatus.ACCEPTED, RideStatus.ON_DESTINATION));
        assertEquals(ride1, actualRide);
    }

    @Test
    public void shouldReturnRideBySenderAndStatus() {
        Ride ride = Ride.builder().sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(ride);
        testEntityManager.flush();

        Ride actualRide = rideRepository.getRideBySenderAndStatus("milic@gmail.com", RideStatus.ACCEPTED);
        assertEquals(ride, actualRide);
    }

    @Test
    public void shouldReturnNullBySenderAndStatusBecauseSenderDoesntExist() {
        Ride ride = Ride.builder().sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(ride);
        testEntityManager.flush();

        Ride actualRide = rideRepository.getRideBySenderAndStatus("m@gmail.com", RideStatus.ACCEPTED);
        assertNull(actualRide);
    }

    @Test
    public void shouldReturnRidesByChosenStatus() {
        Ride ride1 = Ride.builder().status(RideStatus.ACCEPTED).sender("milic@gmail.com").build();
        Ride ride2 = Ride.builder().status(RideStatus.ACCEPTED).sender("m@gmail.com").build();
        Ride ride3 = Ride.builder().status(RideStatus.STARTED).sender("mi@gmail.com").build();

        testEntityManager.persist(ride1);
        testEntityManager.persist(ride2);
        testEntityManager.persist(ride3);
        testEntityManager.flush();

        List<Ride> actualRides = rideRepository.getRidesByStatus(RideStatus.ACCEPTED);
        assertEquals(2, actualRides.size());
    }

    @Test
    public void shouldReturnRidesWithPassengerAndChosenStatus() {
        Passenger passenger = Passenger.builder().passwordHash("hash").email("putnik@gmail.com").name("Marko").surname("Markovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride = Ride.builder().passengers(new HashSet<>(Collections.singletonList(passenger))).sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(passenger);
        testEntityManager.persist(ride);
        testEntityManager.flush();

        List<Ride> actualRides = rideRepository.getAllByPassengersContainingAndStatus(passenger, RideStatus.ACCEPTED);
        assertEquals(ride, actualRides.get(0));
    }

    @Test
    public void shouldReturnEmptyListBecauseOfMissingPassenger() {
        Passenger passenger = Passenger.builder().passwordHash("hash").email("putnik@gmail.com").name("Marko").surname("Markovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride = Ride.builder().sender("milic@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(passenger);
        testEntityManager.persist(ride);
        testEntityManager.flush();

        List<Ride> actualRides = rideRepository.getAllByPassengersContainingAndStatus(passenger, RideStatus.ACCEPTED);
        assertEquals(0, actualRides.size());
    }

    @Test
    public void shouldReturnListOfRidesWithDriverAndStatus() {
        Driver driver = Driver.builder().passwordHash("hash").email("test1@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Ride ride1 = Ride.builder().driver(driver).sender("mi@gmail.com").status(RideStatus.ACCEPTED).build();
        Ride ride2 = Ride.builder().driver(driver).sender("test@gmail.com").status(RideStatus.ACCEPTED).build();

        testEntityManager.persist(driver);
        testEntityManager.persist(ride1);
        testEntityManager.persist(ride2);
        testEntityManager.flush();

        List<Ride> actualRides = rideRepository.getAllByDriverAndStatus(driver, RideStatus.ACCEPTED);
        assertEquals(2, actualRides.size());
        assertEquals(ride1, actualRides.get(0));
        assertEquals(ride2, actualRides.get(1));
    }


}
