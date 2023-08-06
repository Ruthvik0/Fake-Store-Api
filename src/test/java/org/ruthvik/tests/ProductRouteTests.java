package org.ruthvik.tests;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.ruthvik.annotation.Authors;
import org.ruthvik.api.CategoryApi;
import org.ruthvik.api.ProductApi;
import org.ruthvik.assertions.ErrorMessage;
import org.ruthvik.assertions.ResponseAssert;
import org.ruthvik.enums.ErrorType;
import org.ruthvik.pojo.request.ProductRequest;
import org.ruthvik.pojo.response.CategoryResponse;
import org.ruthvik.pojo.response.ProductResponse;
import org.ruthvik.utils.DataUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

@Authors(authors = {"Ruthvik","Betrex"})
public final class ProductRouteTests {
	
	@Test(groups = { "positive" })
	public void getAllProducts() {
		Response response = new ProductApi().getAllProducts();
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("products-schema").assertAll();
	}
	
	@Test(groups = { "positive" })
	public void productRouteEndToEndTest() {
		
		ProductRequest productData = DataUtils.getValidProductData();
		
		// Creating new product
		Response postResponse = new ProductApi().createProduct(productData);
		ResponseAssert.assertThat(postResponse).isPostRequestSuccessful().validateResponseSchema("single-product-schema")
				.assertAll();

		long createdProdcutId = postResponse.as(ProductResponse.class).getId();

		// Getting created product
		Response getResponse = new ProductApi().getProduct(createdProdcutId);
		ResponseAssert.assertThat(getResponse).isGetRequestSuccessful().validateResponseSchema("single-product-schema")
				.assertAll();
		Assert.assertEquals(getResponse.as(ProductResponse.class).getId(), createdProdcutId);

		// updating data
		productData.setTitle("updatedProductName");
		productData.setPrice(2000);

		// Calling PUT method with updated data
		Response putResponse = new ProductApi().updateProduct(createdProdcutId, productData);
		ResponseAssert.assertThat(getResponse).isPutRequestSuccessful().validateResponseSchema("single-product-schema")
				.assertAll();
		
		ProductResponse updatedProduct = putResponse.as(ProductResponse.class);
		Assert.assertEquals(updatedProduct.getTitle(), "updatedProductName");
		Assert.assertEquals(updatedProduct.getPrice(), 2000);
		Assert.assertEquals(updatedProduct.getId(), createdProdcutId);
		
		// deleting product
		Response deleteResponse = new ProductApi().deleteProduct(updatedProduct.getId());
		ResponseAssert.assertThat(deleteResponse).isDeleteRequestSuccessful().assertAll();
	}
	
	@Test(groups = { "negative" })
	public void getNonExistingProduct() {
		long productId = 0;
		Response response = new ProductApi().getProduct(productId);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.productWithIdNotFound(productId))
		.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}
	
	@Test(groups = { "negative" })
	public void deleteNonExistingProduct() {
		long productId = 0;
		Response response = new ProductApi().deleteProduct(productId);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.productWithIdNotFound(productId))
		.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}

	@Test(groups = { "negative" })
	public void updateNonExistingProduct() {
		long productId = 0;
		ProductRequest updatedProductData = ProductRequest.builder().setTitle("updatedProductName").setPrice(2000).build();
		Response response = new ProductApi().updateProduct(productId,updatedProductData);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage(ErrorMessage.productWithIdNotFound(productId))
		.hasErrorType(ErrorType.ENTITY_NOT_FOUND_ERROR).assertAll();
	}
	
	@Test(groups = { "positive" })
	public void filterProductByTitle() {
		String productTitle = "Handmade";
		Response response = new ProductApi().filterProductByTitle(productTitle);
		convertResponseToProductList(response).forEach(product -> Assert.assertEquals(product.getTitle().contains(productTitle), true));
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("products-schema").assertAll();
	}
	
	@Test(groups = { "positive" })
	public void filterProductByValidPrice() {
		double productPrice = 687;
		Response response = new ProductApi().filterProductByPrice(productPrice);
		convertResponseToProductList(response).forEach(product -> Assert.assertEquals(product.getPrice(), productPrice));
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("products-schema").assertAll();
	}
	
	@Test(groups = { "negative" })
	public void filterProductByInvalidPrice() {
		double productPrice = -50;
		Response response = new ProductApi().filterProductByPrice(productPrice);
		ResponseAssert.assertThat(response).isBadRequestAndHasMessage("price_min must not be less than 0").assertAll();
	}
	
	@Test(groups = { "positive" })
	public void filterProductByValidPriceRange() {
		double minProductPrice = 500;
		double maxProductPrice = 1000;
		Response response = new ProductApi().filterProductByPriceRange(minProductPrice,maxProductPrice);
		convertResponseToProductList(response).forEach(product -> Assertions.assertThat(product.getPrice()).isBetween(minProductPrice, maxProductPrice));
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("products-schema").assertAll();
	}
	@Test(groups= {"negative"})
	public void filterProductByInvalidPriceRange() {
		double minProductPrice = 1500;
		double maxProductPrice = 1000;
		Response response = new ProductApi().filterProductByPriceRange(minProductPrice,maxProductPrice);
		Assert.assertEquals(response.as(ArrayList.class).size(),0);
		ResponseAssert.assertThat(response).isGetRequestSuccessful()
		.assertAll();
	}
	
	@Test(groups = { "positive" })
	public void filterProductByCategory() {
		long categoryId = 1;
		CategoryResponse category = new CategoryApi().getCategory(categoryId).as(CategoryResponse.class);
		Response response = new ProductApi().filterProductByCategory(categoryId);
		convertResponseToProductList(response).forEach(product -> Assert.assertEquals(product.getCategory(), category));
		ResponseAssert.assertThat(response).isGetRequestSuccessful().validateResponseSchema("products-schema").assertAll();
	}

	private List<ProductResponse> convertResponseToProductList(Response response){
		List<ProductResponse> products = new ArrayList<ProductResponse>();
		try {
			products = new ObjectMapper().readValue(response.asByteArray(), new TypeReference<List<ProductResponse>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
}
