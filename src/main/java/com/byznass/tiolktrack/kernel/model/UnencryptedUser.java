package com.byznass.tiolktrack.kernel.model;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UnencryptedUser {

	private static final String ALL_PARAMETERS_MUST_BE_NON_NULL = "All parameters must be non-null";

	private final String userId;
	private final String password;


	public UnencryptedUser(@NotNull String userId, @NotNull String password) {

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

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof UnencryptedUser)) {
			return false;
		}

		UnencryptedUser that = (UnencryptedUser) o;

		return userId.equals(that.userId) &&
				password.equals(that.password);
	}

	@Override
	public int hashCode() {

		return Objects.hash(userId, password);
	}
}
