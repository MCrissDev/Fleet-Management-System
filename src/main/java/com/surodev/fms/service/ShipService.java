package com.surodev.fms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.surodev.fms.domain.Ship;

@Service
public class ShipService extends AbstractService<Ship> {

	@Autowired
	private JpaRepository<Ship, Long> mShipRepository;

	/**
	 * Retrieve long value of the id from a <T> instance
	 * 
	 * @param object valid <T> instance
	 * @return long value of the <T> id
	 */
	@Override
	public long getObjectId(Ship object) {
		return object.getId();
	}

	/**
	 * Retrieve service repository.
	 * 
	 * @return valid repository
	 */
	@Override
	public JpaRepository<Ship, Long> getRepository() {
		return mShipRepository;
	}
}