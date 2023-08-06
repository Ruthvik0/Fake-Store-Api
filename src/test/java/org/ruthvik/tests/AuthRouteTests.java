package org.ruthvik.tests;

import org.ruthvik.annotation.Authors;
import org.ruthvik.api.AuthApi;
import org.ruthvik.api.UserApi;
import org.ruthvik.assertions.ResponseAssert;
import org.ruthvik.pojo.request.LoginRequest;
import org.ruthvik.pojo.response.UserResponse;
import org.ruthvik.utils.DataUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

@Authors(authors = {"John Doe","Susan"})
public final class AuthRouteTests {
	
	@Test(groups = {"positive"})
	public void loginUserAndVerfiyProfile() {
		final long userId = 1;
		Response response  = new UserApi().getUser(userId);
		
		ResponseAssert.assertThat(response)
			.isGetRequestSuccessful()
			.validateResponseSchema("single-user-schema")
			.assertAll();
		
		UserResponse user = response.as(UserResponse.class);
		
		LoginRequest userCredentials = LoginRequest.builder()
				.setEmail(user.getEmail())
				.setPassword(user.getPassword())
				.build();
		
		Response profile = new AuthApi().userProfile(userCredentials);
		
		ResponseAssert.assertThat(profile)
			.isGetRequestSuccessful()
			.validateResponseSchema("single-user-schema")
			.assertAll();
		
		Assert.assertEquals(user, profile.as(UserResponse.class));
	}
	
	@Test(groups = {"positive"})
	public void loginWithValidCredentials() {
		LoginRequest userCredentials = LoginRequest.builder().setEmail("john@mail.com").setPassword("changeme").build();
		Response response = new AuthApi().loginUser(userCredentials);
		ResponseAssert.assertThat(response).isPostRequestSuccessful().validateResponseSchema("auth-response-schema").assertAll();
	}
	
	@Test(dataProvider = "getInvalidLoginData",groups = { "negative" })
	public void loginWithInvalidCredentials(LoginRequest userCredentials ) {
		Response response = new AuthApi().loginUser(userCredentials);
		ResponseAssert.assertThat(response).isUnauthorized().assertAll();
	}
	
	@DataProvider
	public Object[] getInvalidLoginData() {
		return new Object[] {DataUtils.getInvalidLoginData(), DataUtils.getLoginDataWithout("email"), DataUtils.getLoginDataWithout("password")};
	}
}
