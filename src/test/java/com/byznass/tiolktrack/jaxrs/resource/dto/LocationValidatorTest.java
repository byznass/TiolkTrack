package com.byznass.tiolktrack.jaxrs.resource.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationValidatorTest {

	private LocationValidator locationValidator;

	@Before
	public void setUp() {

		locationValidator = new LocationValidator();
	}

	@Test(expected = InvalidDtoException.class)
	public void givenDtoWithTimeThenThrowException() {

		Location location = new Location("xxx", "yyy", "invalid");

		locationValidator.validate(location);
	}

	public void givenValidDtoThenReturnSuccessfully() {

		Location location = new Location("xxx", "yyy", null);

		locationValidator.validate(location);
	}
}