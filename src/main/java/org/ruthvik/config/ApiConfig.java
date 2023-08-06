package org.ruthvik.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({ "file:${user.dir}/src/test/resources/api-config.properties",
		"file:${user.dir}/src/test/resources/prod-config.properties",
		"file:${user.dir}/src/test/resources/staging-config.properties", "system:properties", "system:env" })

public interface ApiConfig extends Config {

	@Key("env")
	@DefaultValue("prod")
	String environment();

	@Key("${env}.base.url")
	String baseUrl();
	
	@Key("users.endpoint")
	String usersEndpoint();
	
	@DefaultValue("${users.endpoint}/%s")
	String singleUserEndpoint(final long userID);
	
	@Key("products.endpoint")
	String productsEndpoint();
	
	@DefaultValue("${products.endpoint}/%s")
	String singleProductEndpoint(final long productID);
	
	@DefaultValue("${auth.endpoint}/login")
	String loginEndpoint();
	
	@DefaultValue("${auth.endpoint}/profile")
	String profileEndpoint();
	
	@Key("categories.endpoint")
	String categoriesEndpoint();
	
	@DefaultValue("${categories.endpoint}/%s")
	String singleCategoryEndpoint(final long categoryID);
	
	@DefaultValue("${categories.endpoint}/%s/products")
	String productsByCategory(final long categoryID);
	
}