package com.byznass.tiolktrack.jaxrs.resource.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private static final String ALL_PARAMETERS_MUST_BE_NON_NULL = "All parameters must be non-null";

	private final String userId;
	private final String password;

	@JsonCreator
	public User(@JsonProperty("userId") String userId, @JsonProperty("password") String password) {

		if (userId == null || password == null) {
			throw new IllegalArgumentException(ALL_PARAMETERS_MUST_BE_NON_NULL);
		}

		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {

		return userId;
	}

	public String getPassword() {

		return password;
	}
}
