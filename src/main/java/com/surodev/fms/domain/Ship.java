package com.surodev.fms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import static com.surodev.fms.common.Constants.COLUMN_SHIP_ID;
import static com.surodev.fms.common.Constants.COLUMN_OWNER_ID;
import static com.surodev.fms.common.Constants.COLUMN_SHIP_NAME;
import static com.surodev.fms.common.Constants.COLUMN_IMO_NUMBER;
import static com.surodev.fms.common.Constants.ID_TEXT;
import static com.surodev.fms.common.Constants.SHIPS_OWNERS_TABLE;
import static com.surodev.fms.common.Constants.SHIPS_TABLE;

@Entity
@Table(name = SHIPS_TABLE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ID_TEXT)
public class Ship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(name = COLUMN_SHIP_NAME)
	private String mShipName;

	@Column(name = COLUMN_IMO_NUMBER)
	private long mImoNumber;

	@ManyToMany
	@JoinTable(name = SHIPS_OWNERS_TABLE, joinColumns = @JoinColumn(name = COLUMN_SHIP_ID), inverseJoinColumns = @JoinColumn(name = COLUMN_OWNER_ID))
	@JsonBackReference
	private Set<Owner> owners = new HashSet<Owner>();

	@OneToOne(mappedBy = "ship")
	private Category category;

	/**
	 * Constructor
	 */
	public Ship() {
		super();
	}

	/**
	 * Retrieve IMO number
	 * 
	 * @return valid IMO number of the ship
	 */
	public long getImoNumber() {
		return mImoNumber;
	}

	/**
	 * Retrieve ship name
	 * 
	 * @return ship name
	 */
	public String getShipName() {
		return mShipName;
	}

	/**
	 * Retrieve ship Id
	 * 
	 * @return valid unique id of the ship
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set ship name
	 * 
	 * @param name String representing new ship name
	 */
	public void setShipName(String name) {
		mShipName = name;
	}

	/**
	 * Set ship IMO number
	 * 
	 * @param imo_number valid ship IMO number
	 */
	public void setImoNumber(long imo_number) {
		mImoNumber = imo_number;
	}

	/**
	 * Retrieve ship owners
	 * 
	 * @return list with all current owners of the ship
	 */
	public Set<Owner> getOwners() {
		return owners;
	}

	/**
	 * Set current owners of the ship
	 * 
	 * @param ownersList valid List with owners of the ship
	 */
	public void setOwners(Set<Owner> ownersList) {
		owners.clear();
		owners.addAll(ownersList);
	}

	/**
	 * Add Owner for the ship
	 * 
	 * @param owner valid Owner instance
	 */
	public void addOwner(Owner owner) {
		if (!owners.contains(owner)) {
			owners.add(owner);
		}
	}

	/**
	 * Set ship category
	 * 
	 * @param newCategory valid Category instance
	 */
	public void setCategory(Category newCategory) {
		if (newCategory != null) {
			newCategory.setShip(this);
		}
		category = newCategory;
	}

	/**
	 * Retrieve ship category
	 * 
	 * @return valid Category instance or null if none set
	 */
	public Category getCategory() {
		return category;
	}
}