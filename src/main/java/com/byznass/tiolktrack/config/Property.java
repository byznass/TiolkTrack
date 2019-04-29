package com.byznass.tiolktrack.config;

public enum Property {

	DB_HOST("DB_HOST"),
	DB_INTEGRATION_TEST_HOST("DB_INTEGRATION_TEST_HOST"),
	DB_USERNAME("DB_USERNAME"),
	DB_PASSWORD("DB_PASSWORD");

	private String envName;

	Property(String envName) {

		this.envName = envName;
	}

	public String getEnvName() {

		return envName;
	}
}