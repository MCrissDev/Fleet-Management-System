package com.surodev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.surodev.fms.common.Log;
import com.surodev.fms.common.Utils;

import static com.surodev.fms.common.Constants.DEBUG;

@SpringBootApplication
public class Application {
	private static final String TAG = Utils.makeTag(Application.class);

	public static void main(String[] args) {
		if (DEBUG) {
			Log.d(TAG, "Initializing app");
		}
		SpringApplication.run(Application.class, args);
	}
}