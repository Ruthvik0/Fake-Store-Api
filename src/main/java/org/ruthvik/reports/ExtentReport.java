package org.ruthvik.reports;

import java.util.Date;
import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentReport {

	private ExtentReport() {

	}

	private static ExtentReports reports;

	public static void initReports() {
		
		final String reportName = "report-"+ new Date().toString().replace(":", "_");

		if (Objects.isNull(reports)) {
			reports = new ExtentReports();
			reports.setSystemInfo("OS", System.getProperty("os.name"));
			reports.setSystemInfo("OS Version", System.getProperty("os.version"));
			reports.setSystemInfo("JAVA", System.getProperty("java.version"));
			
			ExtentSparkReporter reporter = new ExtentSparkReporter("reports/"+reportName+".html");
			
			reporter.config().setTheme(Theme.DARK);
			reporter.config().setDocumentTitle("FakerStoreApi");
			reporter.config().setReportName("Api Test Reports");
			reports.attachReporter(reporter);
		}
	}

	public static void createTest(String name) {
		ExtentTest test = reports.createTest(name);
		ExtentManager.setTest(test);
	}
	
	public static void addAuthors(String[] authors) {
		for(String author: authors) {			
			ExtentManager.getTest().assignAuthor(author);
		}
	}

	public static void flushReports() {
		if (Objects.nonNull(reports)) {
			reports.flush();
			ExtentManager.unloadTest();
		}
	}
}
