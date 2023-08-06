package org.ruthvik.enums;

public enum ErrorType {
	
	ENTITY_NOT_FOUND_ERROR("EntityNotFoundError");

	private final String name;

	private ErrorType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
