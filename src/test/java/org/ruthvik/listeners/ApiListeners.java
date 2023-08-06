package org.ruthvik.listeners;

import org.ruthvik.annotation.Authors;
import org.ruthvik.reports.ExtentLogger;
import org.ruthvik.reports.ExtentReport;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiListeners implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		ExtentReport.initReports();
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentReport.createTest(result.getName());
		if(result.getInstance().getClass().isAnnotationPresent(Authors.class)) {
			ExtentReport.addAuthors(result.getInstance().getClass().getAnnotation(Authors.class).authors());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentLogger.pass(result.getName() + " is passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String errorMessage = result.getThrowable().getMessage();
		ExtentLogger.collapsibleBlock("Error", errorMessage);
		ExtentLogger.fail(result.getName() + " is failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentLogger.skip(result.getName() + " is skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReport.flushReports();
	}

}
