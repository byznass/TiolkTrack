package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.byznass.tiolktrack.kernel.handler.InvalidLocationException.Reason.INVALID_LATITUDE;
import static com.byznass.tiolktrack.kernel.handler.InvalidLocationException.Reason.INVALID_LONGITUDE;

class CoordinateValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateValidator.class);

	private static final BigDecimal MIN_LATITUDE = new BigDecimal("-90");
	private static final BigDecimal MAX_LATITUDE = new BigDecimal("90");
	private static final BigDecimal MIN_LONGITUDE = new BigDecimal("-180");
	private static final BigDecimal MAX_LONGITUDE = new BigDecimal("180");

	void validate(Location location) {

		validateLatitude(location.getLatitude());
		validateLongitude(location.getLongitude());
	}

	private void validateLatitude(String latitude) {

		BigDecimal latitudeValue;
		try {
			latitudeValue = new BigDecimal(latitude);
		} catch (NumberFormatException e) {
			LOGGER.error("Given Latitude is not a BigDecimal");
			throw new InvalidLocationException(INVALID_LATITUDE);
		}

		if (latitudeValue.compareTo(MIN_LATITUDE) < 0 || latitudeValue.compareTo(MAX_LATITUDE) > 0) {
			LOGGER.error("Given Latitude is not between {} and {}", MIN_LATITUDE, MAX_LATITUDE);
			throw new InvalidLocationException(INVALID_LATITUDE);
		}
	}

	private void validateLongitude(String longitude) {

		BigDecimal longitudeValue;
		try {
			longitudeValue = new BigDecimal(longitude);
		} catch (NumberFormatException e) {
			LOGGER.error("Given Longitude is not a BigDecimal");
			throw new InvalidLocationException(INVALID_LONGITUDE);
		}

		if (longitudeValue.compareTo(MIN_LONGITUDE) < 0 || longitudeValue.compareTo(MAX_LONGITUDE) > 0) {
			LOGGER.error("Given Longitude is not between {} and {}", MIN_LONGITUDE, MAX_LONGITUDE);
			throw new InvalidLocationException(INVALID_LONGITUDE);
		}
	}
}
