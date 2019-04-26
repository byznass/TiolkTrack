package com.byznass.tiolktrack.kernel.model.gps;

import static java.util.Objects.requireNonNull;

public class Gps {

	private final String userId;
	private final String name;

	public Gps(String userId, String name) {

		this.userId = requireNonNull(userId, "UserId cannot be null");
		this.name = requireNonNull(name, "Name cannot be null");
	}

	public String getUserId() {

		return userId;
	}

	public String getName() {

		return name;
	}
}
