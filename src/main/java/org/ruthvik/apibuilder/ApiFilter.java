package org.ruthvik.apibuilder;

import java.util.Objects;

import org.ruthvik.reports.ExtentLogger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiFilter implements Filter {

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		logRequest(requestSpec);

		Response response = ctx.next(requestSpec, responseSpec);

		logResponse(response);

		return response;
	}

	private void logRequest(FilterableRequestSpecification requestSpec) {
		ExtentLogger.log(requestSpec.getMethod() + " Request is sent to " + requestSpec.getURI());
		ExtentLogger.collapsibleBlock("Request Headers", requestSpec.getHeaders().toString());
		if (Objects.nonNull(requestSpec.getCookies()) && requestSpec.getCookies().exist()) {
			ExtentLogger.collapsibleBlock("Request Cookies",requestSpec.getCookies().toString());
		}

		if (Objects.nonNull(requestSpec.getBody())) {
			ExtentLogger.collapsibleBlock("Request Body", requestSpec.getBody().toString());
		}
	}

	private void logResponse(Response response) {
		ExtentLogger.log("Status code " + response.getStatusCode());
		if (Objects.nonNull(response.getHeaders()) && response.getHeaders().exist()) {
			ExtentLogger.collapsibleBlock("Response Headers", response.getHeaders().toString());
		}
		if (Objects.nonNull(response.getCookies()) && !response.getCookies().isEmpty()) {
			ExtentLogger.collapsibleBlock("Response Cookies",response.getCookies().toString());
		}
		ExtentLogger.collapsibleBlock("Response Body", response.asPrettyString());
	}

}
