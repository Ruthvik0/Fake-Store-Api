package org.ruthvik.api;

import org.ruthvik.apibuilder.ApiBuilder;
import org.ruthvik.config.ApiConfig;
import org.ruthvik.config.factory.ApiConfigFactory;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.request.UserRequest;

import io.restassured.response.Response;

public final class UserApi {
	private final ApiConfig config;
	public UserApi() {
		this.config = ApiConfigFactory.getConfig();
	}
	
	public Response getAllUsers() {
		return new  ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET, config.usersEndpoint());
	}

	public Response getUser(long userId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET, config.singleUserEndpoint(userId));
	}

	public Response createUser(UserRequest userDetails) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setBody(userDetails)
				.fetchResponse(ApiMethod.POST, config.usersEndpoint());
	}

	public Response updateUser(long userId, UserRequest updatedUserDetails) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setBody(updatedUserDetails)
				.fetchResponse(ApiMethod.PUT, config.singleUserEndpoint(userId));
	}

	public Response deleteUser(long userId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.DELETE,config.singleUserEndpoint(userId));
	}
}
