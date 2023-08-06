package org.ruthvik.apibuilder;

import io.restassured.specification.RequestSpecification;

public interface Authentication {
	void addAuthentication(RequestSpecification requestSpec);
}
