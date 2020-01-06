package com.surodev.fms.exceptions;

/**
 * 
 * ResAlreadyExistsException thrown when a resource already exists in the database
 *
 */
public class ResAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -800454603602898398L;

	public ResAlreadyExistsException() {
	
    }

	public ResAlreadyExistsException(String msg) {
        super(msg);
    }
}