package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.model.Location;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.byznass.tiolktrack.kernel.handler.InvalidLocationException.Reason.INVALID_LATITUDE;
import static com.byznass.tiolktrack.kernel.handler.InvalidLocationException.Reason.INVALID_LONGITUDE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CoordinateValidatorTest {

	private CoordinateValidator coordinateValidator;

	@Before
	public void setUp() {

		coordinateValidator = new CoordinateValidator();
	}

	@Test
	public void givenTooSmallLatitudeThenThrowException() {

		Location location = new Location("-90.00001", "0.00", LocalDateTime.now(), "userId", "gpsName");

		try {
			coordinateValidator.validate(location);
			fail("Call to the validate method must throw an exception.");
		} catch (InvalidLocationException e) {

			assertEquals(INVALID_LATITUDE, e.getReason());
		}
	}

	@Test
	public void givenTooBigLatitudeThenThrowException() {

		Location location = new Location("+90.00001", "0.00", LocalDateTime.now(), "userId", "gpsName");

		try {
			coordinateValidator.validate(location);
			fail("Call to the validate method must throw an exception.");
		} catch (InvalidLocationException e) {

			assertEquals(INVALID_LATITUDE, e.getReason());
		}
	}

	@Test
	public void givenNonBigDecimalLatitudeThenThrowException() {

		Location location = new Location("+90..00001", "0.00", LocalDateTime.now(), "userId", "gpsName");

		try {
			coordinateValidator.validate(location);
			fail("Call to the validate method must throw an exception.");
		} catch (InvalidLocationException e) {

			assertEquals(INVALID_LATITUDE, e.getReason());
		}
	}

	@Test
	public void givenTooSmallLongitudeThenThrowException() {

		Location location = new Location("-90.0000", "-180.0001", LocalDateTime.now(), "userId", "gpsName");

		try {
			coordinateValidator.validate(location);
			fail("Call to the validate method must throw an exception.");
		} catch (InvalidLocationException e) {

			assertEquals(INVALID_LONGITUDE, e.getReason());
		}
	}

	@Test
	public void givenTooBigLongitudeThenThrowException() {

		Location location = new Location("+90.0000", "+180.0000001", LocalDateTime.now(), "userId", "gpsName");

		try {
			coordinateValidator.validate(location);
			fail("Call to the validate method must throw an exception.");
		} catch (InvalidLocationException e) {

			assertEquals(INVALID_LONGITUDE, e.getReason());
		}
	}

	@Test
	public void givenNonBigDecimalLongitudeThenThrowException() {

		Location location = new Location("+90.0000", "0x.00", LocalDateTime.now(), "userId", "gpsName");

		try {
			coordinateValidator.validate(location);
			fail("Call to the validate method must throw an exception.");
		} catch (InvalidLocationException e) {

			assertEquals(INVALID_LONGITUDE, e.getReason());
		}
	}

	@Test
	public void givenValidCoordinatesThenSucceed() {

		Location location = new Location("+66.214560", "-19.23445", LocalDateTime.now(), "userId", "gpsName");

		coordinateValidator.validate(location);
	}
}