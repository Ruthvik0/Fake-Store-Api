package org.ruthvik.reports;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentManager {

	private ExtentManager() {
	}

	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	static ExtentTest getTest() {
		return test.get();
	}

	static void setTest(ExtentTest reftest) {
		test.set(reftest);
	}

	static void unloadTest() {
		test.remove();
	}

}
