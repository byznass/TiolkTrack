package com.byznass.tiolktrack.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.byznass.tiolktrack.config.Property.DB_PASSWORD;
import static com.byznass.tiolktrack.config.Property.DB_USERNAME;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PropertyProvider.class)
public class PropertyProviderTest {

	private PropertyProvider propertyProvider;

	@Before
	public void setUp() {

		mockStatic(System.class);

		propertyProvider = new PropertyProvider();
	}

	@Test(expected = NoSuchPropertyException.class)
	public void givenNoEnvVariableForPropertyThenThrowException() {

		when(System.getenv(DB_USERNAME.getEnvName())).thenReturn(null);

		propertyProvider.getValue(DB_USERNAME);
	}

	@Test
	public void givenExistentEnvVariableThenReturnItsValue() {

		String expectedValue = "abc";
		when(System.getenv(DB_PASSWORD.getEnvName())).thenReturn(expectedValue);

		String actualValue = propertyProvider.getValue(DB_PASSWORD);

		assertEquals(expectedValue, actualValue);
	}

}