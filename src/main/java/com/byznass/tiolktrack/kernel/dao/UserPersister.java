package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.model.User;

public interface UserPersister {

	void persist(User user);
}
