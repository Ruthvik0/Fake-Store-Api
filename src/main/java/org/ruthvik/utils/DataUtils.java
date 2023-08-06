package org.ruthvik.utils;

import java.util.List;

import org.ruthvik.enums.Invalid;
import org.ruthvik.pojo.request.CategoryRequest;
import org.ruthvik.pojo.request.LoginRequest;
import org.ruthvik.pojo.request.ProductRequest;
import org.ruthvik.pojo.request.UserRequest;

import com.github.javafaker.Faker;

public interface DataUtils {

	final Faker faker = new Faker();

	static UserRequest getValidUserData() {
		return UserRequest.builder().setEmail(faker.internet().emailAddress()).setName(faker.name().fullName())
				.setPassword(faker.internet().password()).setAvatar(faker.avatar().image()).build();
	}

	static UserRequest getUserDataWithout(String ignoreField) {
		UserRequest userData = UserRequest.builder().setEmail(faker.internet().emailAddress())
				.setName(faker.name().fullName()).setPassword(faker.internet().password())
				.setAvatar("https://api.lorem.space/image/face?w=640&h=480&r=867").build();
		if (ignoreField.equalsIgnoreCase("email")) {
			userData.setEmail("");
		} else if (ignoreField.equalsIgnoreCase("name")) {
			userData.setName("");
		} else {
			userData.setPassword("");
		}
		return userData;
	}

	static UserRequest getInvalidUserData(Invalid invalid) {
		UserRequest userData = UserRequest.builder().setEmail("Dummy@gmail.com").setName("Dummy")
				.setPassword("Dummy123").setAvatar("https://api.lorem.space/image/face?w=640&h=480&r=867").build();
		if (invalid.equals(Invalid.INVALID_EMAIL)) {
			userData.setEmail("Dummy");
		} else if (invalid.equals(Invalid.INVALID_PASSWORD)) {
			userData.setPassword("@@p@@@");
		} else {
			userData.setPassword("1a");
		}
		return userData;
	}
	
	
	static LoginRequest getLoginDataWithout(String ignoreField) {
		LoginRequest userData = LoginRequest.builder().setEmail("john@mail.com").setPassword("changeme").build();
		if (ignoreField.equalsIgnoreCase("email")) {
			userData.setEmail("");
		} else {
			userData.setPassword("");
		}
		return userData;
	}
	
	static LoginRequest getInvalidLoginData() {
		return LoginRequest.builder().setEmail("Dummy@gmail.com").setPassword("Dummy123").build();
	}

	static CategoryRequest getValidCategoryData() {
		return CategoryRequest.builder()
				.setName(faker.commerce().department())
				.setImage(faker.avatar().image())
				.build();
	}
	
	static CategoryRequest getCategoryDataWithout(String ignoreField) {
		CategoryRequest data = CategoryRequest.builder().setName(faker.commerce().department())
				.setImage(faker.avatar().image()).build();
		if (ignoreField.equalsIgnoreCase("name")) {
			data.setName("");
		} else {
			data.setImage("");
		}
		return data;
	}

	static Object getCategoryDataWithInvalidImage() {
		return CategoryRequest.builder().setName(faker.commerce().department())
				.setImage("Dummy").build();
	}

	static ProductRequest getValidProductData() {
		return ProductRequest.builder()
				.setTitle(faker.commerce().productName())
				.setDescription(faker.lorem().characters())
				.setPrice(Double.valueOf(faker.commerce().price()))
				.setImages(List.of(faker.avatar().image()))
				.setCategoryId(5)
				.build();
	}

}
