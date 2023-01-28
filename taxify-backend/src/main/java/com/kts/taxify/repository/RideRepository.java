package com.kts.taxify.repository;

import com.kts.taxify.model.Ride;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
}
