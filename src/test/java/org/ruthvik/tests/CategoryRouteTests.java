package org.ruthvik.tests;

import java.util.ArrayList;

import org.ruthvik.annotation.Authors;
import org.ruthvik.api.CategoryApi;
import org.ruthvik.assertions.ErrorMessage;
import org.ruthvik.assertions.ResponseAssert;
import org.ruthvik.enums.ErrorType;
import org.ruthvik.pojo.request.CategoryRequest;
import org.ruthvik.pojo.response.CategoryResponse;
import org.ruthvik.utils.DataUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

@Authors(authors = {"Ruthvik","Susan"})
public final class CategoryRouteTests {
	
	@Test(groups = { "positive" })
	public void getAllCategory() {
		Response response = new CategoryApi().getAllCategories();
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("categories-schema").assertAll();
	}
	
	@Test(groups = { "positive" })
	public void categoryRouteEndToEndTest() {
		
		CategoryRequest categoryData = DataUtils.getValidCategoryData();
		
		// Creating new category
		Response postResponse = new CategoryApi().createCategory(categoryData);
		ResponseAssert.assertThat(postResponse).isPostRequestSuccessful().validateResponseSchema("single-category-schema")
				.assertAll();

		long createdCategoryId = postResponse.as(CategoryResponse.class).getId();

		// Getting created category
		Response getResponse = new CategoryApi().getCategory(createdCategoryId);
		ResponseAssert.assertThat(getResponse).isGetRequestSuccessful().validateResponseSchema("single-category-schema")
				.assertAll();
		Assert.assertEquals(getResponse.as(CategoryResponse.class).getId(), createdCategoryId);

		// updating data
		categoryData.setName("updatedCategoryName");
		categoryData.setImage("https://placeimg.com/640/480/any?r=0.591926261873231");

		// Calling PUT method with updated data
		Response putResponse = new CategoryApi().updateCategory(createdCategoryId, categoryData);
		ResponseAssert.assertThat(getResponse).isPutRequestSuccessful().validateResponseSchema("single-category-schema")
				.assertAll();
		
		CategoryResponse updatedCategory = putResponse.as(CategoryResponse.class);
		Assert.assertEquals(updatedCategory.getName(), "updatedCategoryName");
		Assert.assertEquals(updatedCategory.getImage(), "https://placeimg.com/640/480/any?r=0.591926261873231");
		Assert.assertEquals(updatedCategory.getId(), createdCategoryId);
		
		// deleting category
		Response deleteResponse = new CategoryApi().deleteCategory(updatedCategory.getId());
		ResponseAssert.assertThat(deleteResponse).isDeleteRequestSuccessful().assertAll();
	}
	
	@Test(groups = { "negative" })
	public void getNonExistingCategory() {
		long categoryId = 0;
		Response response = new CategoryApi().getCategory(categoryId);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.categoryWithIdNotFound(categoryId))
				.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}
	
	@Test(groups = { "negative" })
	public void deleteNonExistingCategory() {
		long categoryId = 0;
		Response response = new CategoryApi().deleteCategory(categoryId);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.categoryWithIdNotFound(categoryId))
		.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}

	@Test(groups = { "negative" })
	public void updateNonExistingCategory() {
		long categoryId = 0;
		CategoryRequest categoryData = CategoryRequest.builder().setName("updatedName").setImage("https://placeimg.com/640/480/any?r=0.591926261873231").build();
		Response response = new CategoryApi().updateCategory(categoryId, categoryData);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.categoryWithIdNotFound(categoryId))
		.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}
	
	@Test(dataProvider = "getInvalidCategoryData",groups = { "negative" })
	public void createCategoryWithInvalidData(CategoryRequest invalidCategoryData,String errorMessage) {
		Response response = new CategoryApi().createCategory(invalidCategoryData);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessageInList(errorMessage).assertAll();
	}
	
	@DataProvider
	public Object[][] getInvalidCategoryData() {
		return new Object[][] { { DataUtils.getCategoryDataWithInvalidImage(), "image must be a URL address" },
				{ DataUtils.getCategoryDataWithout("name"), "name should not be empty" },
				{ DataUtils.getCategoryDataWithout("image"), "image should not be empty" }};
	}
	
	@Test(groups = { "positive" })
	public void getAllProdcutsByValidCategoryId() {
		Response response = new CategoryApi().getAllProductsByCategory(1);
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("products-schema").assertAll();
	}
	
	@Test(groups = { "negative" })
	public void getAllProdcutsByInvalidCategoryId() {
		Response response = new CategoryApi().getAllProductsByCategory(0);
		Assert.assertEquals(response.as(ArrayList.class).size(),0);
		ResponseAssert.assertThat(response).isGetRequestSuccessful()
		.assertAll();
	}
	
}
