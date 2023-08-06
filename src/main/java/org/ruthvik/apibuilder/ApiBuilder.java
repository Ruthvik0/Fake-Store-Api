package org.ruthvik.apibuilder;

import java.util.Objects;

import org.ruthvik.enums.ApiMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiBuilder {

	private final RequestSpecBuilder requestSpecBuilder;
	private Authentication authentication;

	public ApiBuilder() {
		this.requestSpecBuilder = new RequestSpecBuilder();
		this.authentication = null;
	}

	public ApiBuilder setBaseUrl(String baseUrl) {
		requestSpecBuilder.setBaseUri(baseUrl);
		return this;
	}

	public ApiBuilder setHeaders(String header , String value) {
		requestSpecBuilder.addHeader(header, value);
		return this;
	}

	public ApiBuilder setBody(Object requestBody) {
		requestSpecBuilder.setBody(requestBody);
		return this;
	}

	public ApiBuilder setQueryParams(String query, Object param) {
		requestSpecBuilder.addQueryParam(query, param);
		return this;
	}
	
	public ApiBuilder setAuthentication(Authentication authentication) {
       this.authentication = authentication;
        return this;
    }

	//.filter(new ApiFilter())
	public Response fetchResponse(ApiMethod method, String endpoint) {
		RequestSpecification requestSpec = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.spec(this.requestSpecBuilder.build());
		if (Objects.nonNull(authentication)) {
            authentication.addAuthentication(requestSpec);
        }
		return getResponse(requestSpec, method, endpoint);
	}
	
	protected Response getResponse(RequestSpecification requestSpec, ApiMethod method, String endpoint) {
		if (method.equals(ApiMethod.GET)) {
			return requestSpec.get(endpoint).thenReturn();
		} else if (method.equals(ApiMethod.POST)) {
			return requestSpec.post(endpoint).thenReturn();
		} else if (method.equals(ApiMethod.PUT)) {
			return requestSpec.put(endpoint).thenReturn();
		} else if (method.equals(ApiMethod.DELETE)) {
			return requestSpec.delete(endpoint).thenReturn();
		} else {
			return requestSpec.patch(endpoint).thenReturn();
		}
	}
	
}
