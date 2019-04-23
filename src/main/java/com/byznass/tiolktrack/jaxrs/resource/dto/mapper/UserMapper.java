package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.User;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;

public class UserMapper {

	public UnencryptedUser toModel(User user) {

		return new UnencryptedUser(user.getUserId(), user.getPassword());
	}
}
