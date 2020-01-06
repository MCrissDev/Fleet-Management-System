package com.surodev.fms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.surodev.fms.domain.Owner;

@Service
public class OwnerService extends AbstractService<Owner> {

	@Autowired
	private JpaRepository<Owner, Long> mOwnerRepository;

	/**
	 * Retrieve long value of the id from a Owner instance
	 * 
	 * @param object valid Owner instance
	 * @return long value of the Owner id
	 */
	@Override
	public long getObjectId(Owner object) {
		return object.getId();
	}

	/**
	 * Retrieve service repository.
	 * 
	 * @return valid repository
	 */
	@Override
	public JpaRepository<Owner, Long> getRepository() {
		return mOwnerRepository;
	}
}