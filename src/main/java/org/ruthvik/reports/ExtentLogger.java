package org.ruthvik.reports;

public class ExtentLogger {
	private ExtentLogger() {
	}

	public static void log(String message) {
		ExtentManager.getTest().info(message);
	}

	public static void pass(String message) {
		ExtentManager.getTest().pass(message);
	}

	public static void fail(String message) {
		ExtentManager.getTest().fail(message);
	}

	public static void skip(String message) {
		ExtentManager.getTest().skip(message);
	}
	
	public static void collapsibleBlock(String heading,String body) {
		StringBuilder builder = new StringBuilder();
		builder.append("<details>");
		builder.append(String.format("<summary>%s</summary>", heading));
		builder.append(String.format("<pre>%s</pre>", body));
		builder.append("</details>");
		log(builder.toString());
	}
}
