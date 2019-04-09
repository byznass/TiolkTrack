package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.config.TimeProvider;
import com.byznass.tiolktrack.jaxrs.resource.dto.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocationMapperTest {

	@Mock
	private TimeProvider timeProvider;

	private LocationMapper locationMapper;

	@Before
	public void setUp() {

		initMocks(this);

		locationMapper = new LocationMapper(timeProvider);
	}

	@Test
	public void givenModelLocationWhenMappingToDotThenDoItCorrectly() {

		String gpsID = "xxx";
		LocalDateTime time = LocalDateTime.of(2019, 4, 6, 10, 15, 20, 0);
		com.byznass.tiolktrack.kernel.model.Location modelLocation =
				new com.byznass.tiolktrack.kernel.model.Location("123", "456", time, gpsID);

		Location actualLocation = locationMapper.toDto(modelLocation);

		Location expectedLocation = new Location("123", "456", "2019-04-06T10:15:20");
		assertEquals(expectedLocation, actualLocation);
	}

	@Test
	public void givenDtoLocationWhenMappingToModelThenDoItCorrectly() {

		LocalDateTime time = LocalDateTime.now();
		Location locationDto = new Location("xxx", "yyy", "ignored");

		when(timeProvider.getCurrentTime()).thenReturn(time);

		com.byznass.tiolktrack.kernel.model.Location actualModel = locationMapper.toModel(locationDto, "xyz");

		com.byznass.tiolktrack.kernel.model.Location expectedModel =
				new com.byznass.tiolktrack.kernel.model.Location("xxx", "yyy", time, "xyz");
		assertEquals(expectedModel, actualModel);
	}
}