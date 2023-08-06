package org.ruthvik.apibuilder;

import io.restassured.specification.RequestSpecification;

public class BasicAuthentication implements Authentication {

	private String email;
	private String password;

	public BasicAuthentication(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public void addAuthentication(RequestSpecification requestSpec) {
		requestSpec.auth().preemptive().basic(email, password);
	}

}
