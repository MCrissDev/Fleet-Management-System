package com.surodev.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surodev.fms.domain.Ship;

/**
 * Repository used for Ship data manipulations
 *
 */
@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
}