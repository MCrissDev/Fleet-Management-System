package com.surodev.fms.common;

/**
 * Constants class
 * Used to contain common constants across the project
 */
public class Constants {

	public static final String BEGIN_TAG = "FLEET_";
	public static final boolean DEBUG = true;
	
	// API
	public static final String API = "api";
	public static final String ID_TEXT = "id";
	
	// Table names
	public static final String SHIPS_TABLE = "ships";
	public static final String CATEGORY_TABLE = "category";
	public static final String OWNERS_TABLE = "owner";
	public static final String SHIPS_OWNERS_TABLE = "ships_owners";
	
	// Column names
	public static final String COLUMN_SHIP_ID = "ship_id";
	public static final String COLUMN_SHIP_TYPE = "ship_type";
	public static final String COLUMN_SHIP_TONNAGE = "ship_tonnage";
	
	public static final String COLUMN_OWNER_ID = "owner_id";
	public static final String COLUMN_OWNER_NAME = "owner_name";
	
	public static final String COLUMN_SHIP_NAME = "ship_name";
	public static final String COLUMN_IMO_NUMBER = "imo_number";
}