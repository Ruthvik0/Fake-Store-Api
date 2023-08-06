package org.ruthvik.api;

import org.ruthvik.apibuilder.ApiBuilder;
import org.ruthvik.config.ApiConfig;
import org.ruthvik.config.factory.ApiConfigFactory;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.request.ProductRequest;

import io.restassured.response.Response;

public final class ProductApi {
	private final ApiConfig config;

	public ProductApi() {
		this.config = ApiConfigFactory.getConfig();
	}
	
	public Response getAllProducts() {
		return new  ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET, config.productsEndpoint());
	}

	public Response getProduct(long productId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.GET, config.singleProductEndpoint(productId));
	}
	
	public Response createProduct(ProductRequest productDetails) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setBody(productDetails)
				.fetchResponse(ApiMethod.POST, config.productsEndpoint());
	}
	
	public Response updateProduct(long productId, ProductRequest updatedProductDetails) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setBody(updatedProductDetails)
				.fetchResponse(ApiMethod.PUT, config.singleProductEndpoint(productId));
	}

	public Response deleteProduct(long productId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.fetchResponse(ApiMethod.DELETE,config.singleProductEndpoint(productId));
	}
	
	public Response filterProductByTitle(String productTitle) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setQueryParams("title", productTitle)
				.fetchResponse(ApiMethod.GET,config.productsEndpoint());
	}
	
	public Response filterProductByPrice(double productPrice) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setQueryParams("price", productPrice)
				.fetchResponse(ApiMethod.GET,config.productsEndpoint());
	}
	
	public Response filterProductByPriceRange(double minProductPrice,double maxProductPrice) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setQueryParams("price_min", minProductPrice)
				.setQueryParams("price_max", maxProductPrice)
				.fetchResponse(ApiMethod.GET,config.productsEndpoint());
	}
	
	public Response filterProductByCategory(long categoryId) {
		return new ApiBuilder()
				.setBaseUrl(config.baseUrl())
				.setQueryParams("categoryId", categoryId)
				.fetchResponse(ApiMethod.GET,config.productsEndpoint());
	}
}
