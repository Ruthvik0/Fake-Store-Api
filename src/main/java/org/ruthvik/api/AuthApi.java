package org.ruthvik.api;

import org.ruthvik.apibuilder.ApiBuilder;
import org.ruthvik.apibuilder.BearerTokenAuthentication;
import org.ruthvik.config.ApiConfig;
import org.ruthvik.config.factory.ApiConfigFactory;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.request.LoginRequest;

import io.restassured.response.Response;

public final class AuthApi {
	private final ApiConfig config;
	public AuthApi() {
		this.config = ApiConfigFactory.getConfig();
	}
	
	public Response loginUser(LoginRequest userCredentials) {
		return new ApiBuilder()
		.setBaseUrl(config.baseUrl())
		.setBody(userCredentials)
		.fetchResponse(ApiMethod.POST, config.loginEndpoint());
	}
	
	public Response userProfile(LoginRequest userCredentials) {
		return new ApiBuilder()
		.setBaseUrl(config.baseUrl())
		.setAuthentication(new BearerTokenAuthentication(userCredentials))
		.fetchResponse(ApiMethod.GET, config.profileEndpoint());
	}
	

}
