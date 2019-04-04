package com.byznass.tiolktrack.config;

public class PropertyProvider {

	public String getValue(Property property) throws NoSuchPropertyException {

		String value = System.getenv(property.getEnvName());

		if (value == null) {
			throw new NoSuchPropertyException(String.format("No environment variable %s", property.getEnvName()));
		}

		return value;
	}
}
