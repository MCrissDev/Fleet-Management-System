package com.surodev.fms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import static com.surodev.fms.common.Constants.OWNERS_TABLE;
import static com.surodev.fms.common.Constants.ID_TEXT;
import static com.surodev.fms.common.Constants.COLUMN_OWNER_ID;
import static com.surodev.fms.common.Constants.COLUMN_OWNER_NAME;

/**
 * Class representing the Owner object. The Owner entity is mapped to the owner
 * table which will provide owner informations like name
 *
 */
@Entity
@Table(name = OWNERS_TABLE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ID_TEXT)
public class Owner {

	@ManyToMany(mappedBy = "owners")
	private Set<Ship> ships = new HashSet<>();

	@Id
	@Column(name = COLUMN_OWNER_ID)
	@GeneratedValue
	private long mId;

	@NotNull
	@Column(name = COLUMN_OWNER_NAME)
	private String mOwnerName;

	/**
	 * Constructor
	 */
	public Owner() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param name valid String
	 */
	public Owner(String name) {
		mOwnerName = name;
	}

	/**
	 * Retrieve entity id
	 * 
	 * @return valid id of the entity
	 */
	public long getId() {
		return mId;
	}

	/**
	 * Set entity id
	 * 
	 * @param id valid long value
	 */
	public void setId(long id) {
		mId = id;
	}

	/**
	 * Retrieve owner name
	 * 
	 * @return current owner name or null if not set
	 */
	public String getName() {
		return mOwnerName;
	}

	/**
	 * Set owner name
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		mOwnerName = name;
	}

	/**
	 * Retrieve Owner ships
	 * 
	 * @return list with all ships owned
	 */
	public Set<Ship> getOwnerShips() {
		return ships;
	}

	/**
	 * Set owner ships
	 * 
	 * @param shipsList valid list of the ships owner has
	 */
	public void setOwnerShips(Set<Ship> shipsList) {
		ships.clear();
		ships.addAll(shipsList);
	}
}