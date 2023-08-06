package org.ruthvik.api;

import org.ruthvik.apibuilder.ApiBuilder;
import org.ruthvik.config.ApiConfig;
import org.ruthvik.config.factory.ApiConfigFactory;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.request.CategoryRequest;

import io.restassured.response.Response;

public final class CategoryApi {
	private final ApiConfig config;

	public CategoryApi() {
		this.config = ApiConfigFactory.getConfig();
	}
	
	public Response getAllCategories() {
		return new  ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET, config.categoriesEndpoint());
	}

	public Response getCategory(long categoryId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET, config.singleCategoryEndpoint(categoryId));
	}
	
	public Response createCategory(CategoryRequest categoryDetails) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setBody(categoryDetails)
				.fetchResponse(ApiMethod.POST, config.categoriesEndpoint());
	}
	
	public Response updateCategory(long categoryId, CategoryRequest updatedCategoryDetails) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setBody(updatedCategoryDetails)
				.fetchResponse(ApiMethod.PUT, config.singleCategoryEndpoint(categoryId));
	}

	public Response deleteCategory(long categoryId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.DELETE,config.singleCategoryEndpoint(categoryId));
	}
	
	public Response getAllProductsByCategory(long categoryId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET,config.productsByCategory(categoryId));
	}
}
