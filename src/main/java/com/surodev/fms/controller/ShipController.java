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
import com.surodev.fms.domain.Category;
import com.surodev.fms.domain.Owner;
import com.surodev.fms.domain.Ship;
import com.surodev.fms.exceptions.BadResourceException;
import com.surodev.fms.exceptions.ResAlreadyExistsException;
import com.surodev.fms.exceptions.ResourceNotFoundException;
import com.surodev.fms.service.CategoryService;
import com.surodev.fms.service.OwnerService;
import com.surodev.fms.service.ShipService;

import static com.surodev.fms.common.Constants.API;

/**
 * Ships REST controller Handles operations like add ship, get all ships, delete
 * ship, set ship category, set ship owner
 *
 */
@RestController
@RequestMapping(API)
public class ShipController {
	private static final String TAG = Utils.makeTag(ShipController.class);

	@Autowired
	private ShipService mShipService;
	@Autowired
	private OwnerService mOwnerService;
	@Autowired
	private CategoryService mCategoryService;

	/**
	 * Retrieve all Ships from the table
	 * 
	 * @return JSON array containing all ships found
	 */
	@GetMapping(value = "/ships", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ship>> getAllShips() {
		return ResponseEntity.ok(mShipService.getAllObjects());
	}

	/**
	 * Add new Ship to the table
	 * 
	 * @param ship valid Ship instance
	 * @return serialized Ship instance
	 * @throws URISyntaxException
	 */
	@PostMapping(value = "/ships/add")
	public ResponseEntity<Ship> addship(@Valid @RequestBody Ship ship) throws URISyntaxException {
		try {
			Ship newShip = mShipService.createObject(ship);
			return ResponseEntity.created(new URI("/api/ships/" + newShip.getId())).body(ship);
		} catch (ResAlreadyExistsException ex) {
			Log.e(TAG, "addShip: failed to save ship. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} catch (BadResourceException ex) {
			Log.e(TAG, "addShip: failed to save ship. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Add ship owner
	 * 
	 * @param shipId  valid ship id
	 * @param ownerId valid owner id
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/ships/{shipId}/owner/{ownerId}")
	public ResponseEntity<Void> setShipOwner(@PathVariable Long shipId, @PathVariable Long ownerId) {
		try {
			Ship ship = mShipService.getObjectById(shipId);
			Owner owner = mOwnerService.getObjectById(ownerId);
			if (ship.getOwners() != null && ship.getOwners().contains(owner)) {
				Log.e(TAG, "setShipOwner: failed to set ship id = " + shipId + " to owner id = " + ownerId);
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			ship.addOwner(owner);
			mShipService.updateObject(ship);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			Log.e(TAG, "setShipOwner: failed to set ship id = " + shipId + " to owner id = " + ownerId + " . Ex = "
					+ ex.toString());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			Log.e(TAG, "setShipOwner: failed to save owner. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Update ship category
	 * 
	 * @param shipId     valid ship id
	 * @param categoryId valid category id
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/ships/{shipId}/category/{categoryId}")
	public ResponseEntity<Void> setShipCategory(@PathVariable Long shipId, @PathVariable Long categoryId) {
		try {
			Ship ship = mShipService.getObjectById(shipId);
			Category category = mCategoryService.getObjectById(categoryId);
			ship.setCategory(category);
			mShipService.updateObject(ship);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			Log.e(TAG, "setShipOwner: failed to set ship id = " + shipId + " to category id = " + categoryId
					+ " . Ex = " + ex.toString());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			Log.e(TAG, "setShipOwner: failed to save owner. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Remove a ship by id
	 * 
	 * @param shipId valid ship id
	 * @return ResponseEntity
	 */
	@DeleteMapping(path = "/ships/remove/{shipId}")
	public ResponseEntity<Void> deleteShipById(@PathVariable long shipId) {
		try {
			Ship ship = mShipService.getObjectById(shipId);
			mShipService.deleteObject(ship);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			Log.e(TAG, "deleteShipById: failed to delete ship id = " + shipId + ". Ex = " + ex.toString());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException e) {
			Log.e(TAG, "deleteShipById: failed to delete ship. Ex = " + e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Retrieve ship info
	 * 
	 * @param shipId valid ship id
	 * @return serialized Ship object
	 */
	@GetMapping(value = "/ships/info/{shipId}")
	public ResponseEntity<Ship> getShipInfo(@PathVariable long shipId) {
		try {
			Ship ship = mShipService.getObjectById(shipId);
			return ResponseEntity.ok(ship);
		} catch (ResourceNotFoundException ex) {
			Log.e(TAG, "getShipInfo: failed to find ship id = " + shipId + ". Ex = " + ex.toString());
			return ResponseEntity.notFound().build();
		}
	}
}
