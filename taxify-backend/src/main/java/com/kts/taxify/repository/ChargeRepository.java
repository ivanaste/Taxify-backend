package com.kts.taxify.repository;

import com.kts.taxify.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChargeRepository extends JpaRepository<Charge, UUID> {
}