package com.surodev.fms.exceptions;

import java.util.ArrayList;

import java.util.List;

/**
 * BadResourceException Thrown when an object parameter is null
 *
 */
public class BadResourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2944364445241342690L;
	private List<String> errorMessages = new ArrayList<>();

	public BadResourceException() {

	}

	public BadResourceException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @return the errorMessages
	 * 
	 */
	public List<String> getErrorMessages() {
		return errorMessages;

	}

	/**
	 * 
	 * @param errorMessages the errorMessages to set
	 * 
	 */
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;

	}

	/**
	 * Add error message
	 * 
	 * @param message
	 */
	public void addErrorMessage(String message) {
		this.errorMessages.add(message);
	}
}