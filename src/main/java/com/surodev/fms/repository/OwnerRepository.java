package com.surodev.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surodev.fms.domain.Owner;


/**
 * Repository used for Owner data manipulations
 *
 */
@Repository
public interface OwnerRepository  extends JpaRepository<Owner, Long> {
}