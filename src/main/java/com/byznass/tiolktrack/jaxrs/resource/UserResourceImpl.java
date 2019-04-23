package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.User;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.UserMapper;
import com.byznass.tiolktrack.kernel.handler.PersistUserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;

public class UserResourceImpl implements UserResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserResourceImpl.class);

	private final UserMapper userMapper;
	private final PersistUserHandler persistUserHandler;

	@Inject
	public UserResourceImpl(UserMapper userMapper, PersistUserHandler persistUserHandler) {

		this.userMapper = userMapper;
		this.persistUserHandler = persistUserHandler;
	}

	@Override
	public Response createUser(User user) {

		LOGGER.info("Request for creating a user with userId=\'{}\'", user.getUserId());
		persistUserHandler.persistUser(userMapper.toModel(user));
		LOGGER.info("Created a user with userId=\'{}\'", user.getUserId());

		return Response.status(CREATED).build();
	}
}
