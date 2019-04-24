package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.User;

public interface UserProvider {

	User getUser(String userId) throws NoUserWithSuchIdException;
	boolean exists(String userId) throws TiolkTrackException;
}
