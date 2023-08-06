package org.ruthvik.tests;

import org.ruthvik.annotation.Authors;
import org.ruthvik.api.UserApi;
import org.ruthvik.assertions.ErrorMessage;
import org.ruthvik.assertions.ResponseAssert;
import org.ruthvik.enums.ErrorType;
import org.ruthvik.enums.Invalid;
import org.ruthvik.pojo.request.UserRequest;
import org.ruthvik.pojo.response.UserResponse;
import org.ruthvik.utils.DataUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

@Authors(authors = {"Ruthvik","Vignesh"})
public final class UserRouteTests {

	@Test(groups = { "positive" })
	public void getAllUsers() {
		Response response = new UserApi().getAllUsers();
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("users-schema").assertAll();
	}
	
	@Test(groups = { "positive" })
	public void userRouteEndToEndTest() {
		
		UserRequest userData = DataUtils.getValidUserData();
		
		// Creating new User
		Response postResponse = new UserApi().createUser(userData);

		ResponseAssert.assertThat(postResponse).isPostRequestSuccessful().validateResponseSchema("single-user-schema")
				.assertAll();

		long createdUserId = postResponse.as(UserResponse.class).getId();

		// Getting created user
		Response getResponse = new UserApi().getUser(createdUserId);
		ResponseAssert.assertThat(getResponse).isGetRequestSuccessful().validateResponseSchema("single-user-schema")
				.assertAll();
		Assert.assertEquals(getResponse.as(UserResponse.class).getId(), createdUserId);

		// updating data
		userData.setName("updatedName");
		userData.setPassword("updatedPassword");

		// Calling PUT method with updated data
		Response putResponse = new UserApi().updateUser(createdUserId, userData);
		ResponseAssert.assertThat(getResponse).isPutRequestSuccessful().validateResponseSchema("single-user-schema")
				.assertAll();

		UserResponse updatedUser = putResponse.as(UserResponse.class);
		Assert.assertEquals(updatedUser.getName(), "updatedName");
		Assert.assertEquals(updatedUser.getPassword(), "updatedPassword");
		Assert.assertEquals(updatedUser.getId(), createdUserId);

		Response deleteResponse = new UserApi().deleteUser(updatedUser.getId());
		ResponseAssert.assertThat(deleteResponse).isDeleteRequestSuccessful().assertAll();
	}

	@Test(groups = { "negative" })
	public void getNonExistingUser() {
		long userId = 0;
		Response response = new UserApi().getUser(userId);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.userWithIdNotFound(userId))
				.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}

	@Test(groups = { "negative" })
	public void deleteNonExistingUser() {
		long userId = 0;
		Response response = new UserApi().deleteUser(userId);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.userWithIdNotFound(userId))
				.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}

	@Test(groups = { "negative" })
	public void updateNonExistingUser() {
		long userId = 0;
		UserRequest userData = UserRequest.builder().setName("updatedName").setPassword("updatedPassword").build();
		Response response = new UserApi().updateUser(userId, userData);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.userWithIdNotFound(userId))
				.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}

	@Test(dataProvider = "getInvalidUserData", groups = { "negative" })
	public void createUserWithInvalidData(UserRequest invalidUserData, String errorMessage) {
		Response response = new UserApi().createUser(invalidUserData);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessageInList(errorMessage).assertAll();
	}

	@DataProvider
	public Object[][] getInvalidUserData() {
		return new Object[][] { { DataUtils.getInvalidUserData(Invalid.INVALID_EMAIL), "email must be an email" },
				{ DataUtils.getUserDataWithout("email"), "email should not be empty" },
				{ DataUtils.getUserDataWithout("name"), "name should not be empty" },
				{ DataUtils.getUserDataWithout("password"), "password should not be empty" },
				{ DataUtils.getInvalidUserData(Invalid.SHORT_PASSWORD),
						"password must be longer than or equal to 4 characters" },
				{ DataUtils.getInvalidUserData(Invalid.INVALID_PASSWORD),
						"password must contain only letters and numbers" } };
	}
}