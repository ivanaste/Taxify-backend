package com.kts.taxify.repository;

import com.kts.taxify.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    public List<Driver> findAllByCityAndActiveIsTrue(String city);
}
