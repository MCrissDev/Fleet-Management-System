package com.surodev.fms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.surodev.fms.common.Constants.CATEGORY_TABLE;
import static com.surodev.fms.common.Constants.COLUMN_SHIP_ID;
import static com.surodev.fms.common.Constants.COLUMN_SHIP_TYPE;
import static com.surodev.fms.common.Constants.COLUMN_SHIP_TONNAGE;

/**
 * Class representing the Category object. The Category entity is mapped to the
 * category table which will provide ship informations like ship type, tonnage
 *
 */
@Entity
@Table(name = CATEGORY_TABLE)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = COLUMN_SHIP_ID)
	@JsonIgnore
	private Ship ship;

	@NotNull
	@Column(name = COLUMN_SHIP_TYPE)
	private String mShipType;

	@Column(name = COLUMN_SHIP_TONNAGE)
	private long mShipTonnage;

	public Category() {
		super();
	}

	/**
	 * Set ship category
	 * 
	 * @param newShip valid Ship instance
	 */
	public void setShip(Ship newShip) {
		ship = newShip;
	}

	/**
	 * Retrieve Ship assigned to the category
	 * 
	 * @return valid Ship instance or null if none assigned
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Retrieve ship type
	 * 
	 * @return valid String representing ship type
	 */
	public String getShipType() {
		return mShipType;
	}

	/**
	 * Retrieve ship tonnage
	 * 
	 * @return ship tonnage
	 */
	public long getShipTonnage() {
		return mShipTonnage;
	}

	/**
	 * Retrieve category id
	 * 
	 * @return valid id assigned to the category
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set ship type
	 * 
	 * @param type
	 */
	public void setShipType(String type) {
		mShipType = type;
	}

	/**
	 * Set ship tonnage
	 * 
	 * @param tonnage
	 */
	public void setShipTonnage(long tonnage) {
		mShipTonnage = tonnage;
	}

	@Override
	public String toString() {
		return "Category: type = " + getShipType() + " id = " + getId() + " tonnage = " + getShipTonnage() + " ship = "
				+ (ship != null ? ship.toString() : " null");
	}
}