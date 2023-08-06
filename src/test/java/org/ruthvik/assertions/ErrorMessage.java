package org.ruthvik.assertions;

public interface ErrorMessage {
	static String userWithIdNotFound(long userId) {
		return String.format("Could not find any entity of type \"User\" matching: {\n    \"id\": %s\n}" , userId);
	}
	
	static String categoryWithIdNotFound(long categoryId) {
		return String.format("Could not find any entity of type \"Category\" matching: {\n    \"id\": %s\n}" , categoryId);
	}
	
	static String productWithIdNotFound(long productId) {
		return String.format("Could not find any entity of type \"Product\" matching: {\n    \"relations\": [\n        \"category\"\n    ],\n    \"where\": {\n        \"id\": %s\n    }\n}",productId);
	}
}
