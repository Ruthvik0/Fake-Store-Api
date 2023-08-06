package org.ruthvik.apibuilder;

import org.ruthvik.api.AuthApi;
import org.ruthvik.pojo.request.LoginRequest;
import org.ruthvik.pojo.response.LoginResponse;

import io.restassured.specification.RequestSpecification;

public class BearerTokenAuthentication implements Authentication {
	
	private final String token;
	
	public BearerTokenAuthentication(LoginRequest userCredentials) {
		this.token =  new AuthApi().loginUser(userCredentials).as(LoginResponse.class).getAccessToken();
	}

	@Override
	public void addAuthentication(RequestSpecification requestSpec) {
		requestSpec.header("Authorization", "Bearer " + token);

	}

}
