package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.User;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.UserMapper;
import com.byznass.tiolktrack.kernel.handler.PersistUserHandler;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserResourceImplTest {

	private UserResource userResource;

	@Mock
	private PersistUserHandler persistUserHandler;

	@Mock
	private UserMapper userMapper;

	@Before
	public void setUp() {

		initMocks(this);
		userResource = new UserResourceImpl(userMapper, persistUserHandler);
	}

	@Test
	public void givenNewUserWhenCreatingUserThenReturnCreatedStatusCode() {

		User user = new User("igor", "patan");
		UnencryptedUser unencryptedUser = new UnencryptedUser("igor", "patan");
		when(userMapper.toModel(user)).thenReturn(unencryptedUser);

		Response response = userResource.createUser(user);

		assertEquals(CREATED.getStatusCode(), response.getStatus());
		verify(userMapper).toModel(user);
		verify(persistUserHandler).persistUser(unencryptedUser);
	}

}