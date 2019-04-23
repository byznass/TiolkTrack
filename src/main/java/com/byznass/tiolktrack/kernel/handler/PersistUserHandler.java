package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.UserEncrypter;
import com.byznass.tiolktrack.kernel.dao.UserPersister;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import com.byznass.tiolktrack.kernel.model.User;

import javax.inject.Inject;

public class PersistUserHandler {

	private final UserEncrypter userEncrypter;
	private final UserPersister userPersister;
	private final UserProvider userProvider;

	@Inject
	public PersistUserHandler(UserProvider userProvider, UserEncrypter userEncrypter, UserPersister userPersister) {

		this.userProvider = userProvider;
		this.userEncrypter = userEncrypter;
		this.userPersister = userPersister;
	}

	public void persistUser(UnencryptedUser unencryptedUser) {

		try {
			validate(unencryptedUser);
			User user = userEncrypter.encrypt(unencryptedUser);
			userPersister.persist(user);
		} catch (TiolkTrackException e) {
			throw new TiolkTrackException("Failed to create a new user.", e);
		}
	}

	private void validate(UnencryptedUser unencryptedUser) {

		if (unencryptedUser.getUserId().contains(" ")) {
			throw new InvalidUserIdException("User ID cannot contain spaces");
		}

		if (userProvider.exists(unencryptedUser.getUserId())) {
			throw new UserAlreadyExistException("User ID is already taken.");
		}
	}

}
