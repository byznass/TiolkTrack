package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.model.User;

public interface UserProvider {

	User getUser(String userId);
}
