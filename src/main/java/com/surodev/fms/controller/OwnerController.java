package com.surodev.fms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surodev.fms.common.Log;
import com.surodev.fms.common.Utils;
import com.surodev.fms.domain.Owner;
import com.surodev.fms.exceptions.BadResourceException;
import com.surodev.fms.exceptions.ResAlreadyExistsException;
import com.surodev.fms.exceptions.ResourceNotFoundException;
import com.surodev.fms.service.OwnerService;

import static com.surodev.fms.common.Constants.API;

/**
 * Owners REST controller Handles operations like add owner, get all owners,
 * delete owner
 *
 */
@RestController
@RequestMapping(API)
public class OwnerController {
	private static final String TAG = Utils.makeTag(OwnerController.class);

	@Autowired
	private OwnerService mOwnerService;

	/**
	 * Retrieve all owners from the database
	 * 
	 * @return JSON array with all owners saved in the database
	 */
	@GetMapping(value = "/owners", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Owner>> getAllOwners() {
		return ResponseEntity.ok(mOwnerService.getAllObjects());
	}

	/**
	 * Add Owner to the database
	 * 
	 * @param owner valid Owner instance
	 * @return JSON object containing the newly saved object, error instead
	 * @throws URISyntaxException
	 */
	@PostMapping(value = "/owners/add")
	public ResponseEntity<Owner> addOwner(@Valid @RequestBody Owner owner) throws URISyntaxException {
		try {
			Owner newOwner = mOwnerService.createObject(owner);
			return ResponseEntity.created(new URI("/api/owners/" + newOwner.getId())).body(newOwner);
		} catch (ResAlreadyExistsException ex) {
			Log.e(TAG, "addOwner: failed to save owner. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			Log.e(TAG, "addOwner: failed to save owner. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Delete Owner from the database
	 * 
	 * @param ownerId long representing owner Id
	 * @return ResponseEntity status
	 */
	@DeleteMapping(path = "/owners/remove/{ownerId}")
	public ResponseEntity<Void> deleteOwnerById(@PathVariable long ownerId) {
		try {
			Owner owner = mOwnerService.getObjectById(ownerId);
			mOwnerService.deleteObject(owner);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			Log.e(TAG, "deleteOwnerById: failed to delete owner id = " + ownerId + ". Ex = " + ex.toString());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException e) {
			Log.e(TAG, "deleteOwnerById: failed to delete owner. Ex = " + e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}