package com.byznass.tiolktrack.jaxrs.resource.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationValidator.class);

	public void validate(Location location) {

		LOGGER.info("Validating location dto");

		if (location.getTime() != null) {
			LOGGER.error("Failed validation: location.time is non-null");
			throw new InvalidDtoException("location.time field is read-only");
		}

		LOGGER.info("Validation of location dto succeeded");
	}
}
