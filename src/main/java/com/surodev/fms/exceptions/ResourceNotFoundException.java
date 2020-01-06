package com.surodev.fms.exceptions;

/**
 * 
 * ResourceNotFoundException - thrown when a resource couldn't be found in the database
 *
 */
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 3914432272716493561L;

	public ResourceNotFoundException() {

	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
