package com.kts.taxify.repository;

import com.kts.taxify.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldSaveDriver() {
        Driver driver = Driver.builder().passwordHash("hash").email("brzi@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();

        Driver actualDriver = driverRepository.save(driver);

        assertThat(actualDriver).usingRecursiveComparison().ignoringFields("id").isEqualTo(driver);
    }

    @Test
    public void shouldFindAllActiveDriversWhoAreNotReservedByCityName() {
        Driver driver1 = Driver.builder().city("Novi Sad").active(true).reserved(true).passwordHash("hash").email("test1@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").build();
        Driver driver2 = Driver.builder().city("Novi Sad").active(true).reserved(false).passwordHash("hash").email("test2@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").build();
        Driver driver3 = Driver.builder().city("Novi Sad").active(false).reserved(false).passwordHash("hash").email("test3@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").build();

        testEntityManager.persist(driver1);
        testEntityManager.persist(driver2);
        testEntityManager.persist(driver3);
        testEntityManager.flush();

        List<Driver> drivers = driverRepository.findAllByCityAndActiveIsTrueAndReservedIsFalse("Novi Sad");

        assertEquals(1, drivers.size());
    }

    @Test
    public void shouldReturnEmptyListBecauseThereIsntActiveDriversInChosenCity() {
        Driver driver1 = Driver.builder().city("Bijeljina").active(true).reserved(false).passwordHash("hash").email("test1@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Driver driver2 = Driver.builder().city("Novi Sad").active(false).reserved(false).passwordHash("hash").email("test2@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Driver driver3 = Driver.builder().city("Novi Sad").active(false).reserved(false).passwordHash("hash").email("test3@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();

        testEntityManager.persist(driver1);
        testEntityManager.persist(driver2);
        testEntityManager.persist(driver3);
        testEntityManager.flush();

        List<Driver> drivers = driverRepository.findAllByCityAndActiveIsTrueAndReservedIsFalse("Novi Sad");

        assertEquals(0, drivers.size());
    }

    @Test
    public void shouldReturnAllActiveDrivers() {
        Driver driver1 = Driver.builder().city("Bijeljina").active(true).reserved(false).passwordHash("hash").email("test1@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Driver driver2 = Driver.builder().city("Novi Sad").active(false).reserved(false).passwordHash("hash").email("test2@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();
        Driver driver3 = Driver.builder().city("Novi Sad").active(false).reserved(false).passwordHash("hash").email("test3@gmail.com").name("Ivana").surname("Stevanovic").phoneNumber("+38765656426").city("Bijeljina").build();

        testEntityManager.persist(driver1);
        testEntityManager.persist(driver2);
        testEntityManager.persist(driver3);
        testEntityManager.flush();

        List<Driver> drivers = driverRepository.findAllByActive(true);

        assertEquals(1, drivers.size());
    }


}
