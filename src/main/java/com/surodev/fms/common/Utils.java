package com.surodev.fms.common;

import static com.surodev.fms.common.Constants.BEGIN_TAG;

/**
 * Utils class. Contains common functions of the project
 */
public class Utils {

	/**
	 * Helper function, create TAG from class name
	 * 
	 * @param c valid Class
	 * @return tag of the class
	 */
	public static String makeTag(Class<?> c) {
		return BEGIN_TAG + c.getSimpleName();
	}
}