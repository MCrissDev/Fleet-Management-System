package com.surodev.fms.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Log class Use this class in order to print logs in the console. Eg:
 * Log.d(TAG,"this is a sample log")
 */
public class Log {
	private static final String ERROR_STRING = " ERROR ";
	private static final String DEBUG_STRING = " DEBUG ";
	private static final String INFO_STRING = " INFO ";
	private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	private static String getCurrentLocalDateTimeStamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN));
	}

	/**
	 * Print error message log
	 * 
	 * @param tag message tag
	 * @param msg string representing the message to print
	 */
	public static void e(String tag, String msg) {
		String outStr = getCurrentLocalDateTimeStamp() + ERROR_STRING + tag + " : " + msg;
		System.out.println(outStr);
	}

	/**
	 * Print debug message log
	 * 
	 * @param tag message tag
	 * @param msg string representing the message to print
	 */
	public static void d(String tag, String msg) {
		String outStr = getCurrentLocalDateTimeStamp() + DEBUG_STRING + tag + " : " + msg;
		System.out.println(outStr);
	}

	/**
	 * Print info message log
	 * 
	 * @param tag message tag
	 * @param msg string representing the message to print
	 */
	public static void i(String tag, String msg) {
		String outStr = getCurrentLocalDateTimeStamp() + INFO_STRING + tag + " : " + msg;
		System.out.println(outStr);
	}
}