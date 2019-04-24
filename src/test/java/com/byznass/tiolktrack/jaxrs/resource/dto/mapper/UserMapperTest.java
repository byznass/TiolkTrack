package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.User;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

	private UserMapper userMapper;

	@Before
	public void setUp(){
		userMapper = new UserMapper();
	}

	@Test
	public void whenMappingThenReturnCorrectResponse() {

		User user = new User("igor", "patan");
		UnencryptedUser actualResult = userMapper.toModel(user);

		UnencryptedUser expectedResult = new UnencryptedUser("igor", "patan");
		assertEquals(expectedResult, actualResult);
	}

}