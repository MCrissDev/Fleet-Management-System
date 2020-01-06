package com.surodev.fms.service;

import static com.surodev.fms.common.Constants.DEBUG;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surodev.fms.common.Log;
import com.surodev.fms.common.Utils;
import com.surodev.fms.exceptions.BadResourceException;
import com.surodev.fms.exceptions.ResAlreadyExistsException;
import com.surodev.fms.exceptions.ResourceNotFoundException;

/**
 * Abstract class that will allow faster implementation of the services
 *
 * @param <T> one of Ship, Owner, Category
 */
public abstract class AbstractService<T> {

	private static final String TAG = Utils.makeTag(AbstractService.class);

	public AbstractService() {
		super();
	}

	/**
	 * Retrieve all <T> objects found in the database
	 * 
	 * @return List with all found items
	 */
	public List<T> getAllObjects() {
		return getRepository().findAll();
	}

	/**
	 * Find <T> in the database by an id
	 * 
	 * @return valid <T> instance if an item with specified id is found
	 * @throws ResourceNotFoundException if no item with the specified id is found
	 */
	public T getObjectById(long id) throws ResourceNotFoundException {
		T object = getRepository().findById(id).orElse(null);
		if (object == null) {
			throw new ResourceNotFoundException("Cannot find object with id: " + id);
		}
		return object;
	}

	/**
	 * Add new <T> in the database
	 * 
	 * @param object valid instance
	 * @return saved object from the database
	 * @throws BadResourceException      if the parameter is null
	 * @throws ResAlreadyExistsException if <T> with same id already exists in the
	 *                                   database
	 */
	public T createObject(T object) throws BadResourceException, ResAlreadyExistsException {
		if (object != null) {
			try {
				long id = getObjectId(object);
				if (getObjectById(id) != null) {
					throw new ResAlreadyExistsException("Object with id: " + id + " already exists");
				}
			} catch (ResourceNotFoundException e) {
				// Everything's good, let's log the event and proceed further
				if (DEBUG) {
					Log.d(TAG, "createObject: creating new ship");
				}
			}

			return getRepository().save(object);
		}

		BadResourceException exc = new BadResourceException("Failed to save object");
		exc.addErrorMessage("Object is null");
		throw exc;
	}

	/**
	 * Update <T> in the database
	 * 
	 * @param object valid <T> instance
	 * @return updated value from the database
	 * @throws BadResourceException      if the parameter is null
	 * @throws ResourceNotFoundException if no <T> could be found in the database
	 */
	public T updateObject(T object) throws BadResourceException, ResourceNotFoundException {
		if (object != null) {
			// If we couldn't find already a saved Ship in the database, do not proceed
			// further
			getObjectById(getObjectId(object));

			return getRepository().save(object);
		} else {
			BadResourceException exc = new BadResourceException("Failed to update object");
			exc.addErrorMessage("Object is null");
			throw exc;
		}
	}

	/**
	 * Delete <T> from the database
	 * 
	 * @param object valid <T> instance
	 * @return true if the <T> is deleted from database
	 * @throws ResourceNotFoundException if no <T> could be found in the database
	 * @throws BadResourceException      if the parameter is null
	 */
	public boolean deleteObject(T object) throws BadResourceException, ResourceNotFoundException {
		if (object == null) {
			BadResourceException exc = new BadResourceException("Failed to delete object");
			exc.addErrorMessage("Ship is null");
			throw exc;
		}
		// If we couldn't find already a saved Ship in the database, do not proceed
		// further
		getObjectById(getObjectId(object));
		getRepository().delete(object);
		return true;
	}

	/**
	 * Retrieve long value of the id from a <T> instance
	 * 
	 * @param object valid <T> instance
	 * @return long value of the <T> id
	 */
	public abstract long getObjectId(T object);

	/**
	 * Retrieve service repository.
	 * 
	 * @return valid repository
	 */
	public abstract JpaRepository<T, Long> getRepository();
}
