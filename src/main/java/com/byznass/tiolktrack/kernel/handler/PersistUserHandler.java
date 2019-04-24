package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.UserEncrypter;
import com.byznass.tiolktrack.kernel.dao.UserPersister;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import com.byznass.tiolktrack.kernel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class PersistUserHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistUserHandler.class);

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
			LOGGER.info("Creating a new user.");
			validate(unencryptedUser);
			User user = userEncrypter.encrypt(unencryptedUser);
			userPersister.persist(user);
			LOGGER.info("Successfully created a new user.");
		} catch (TiolkTrackException e) {
			LOGGER.error("Failed to create a new user", e);
			throw new TiolkTrackException("Failed to create a new user.", e);
		}
	}

	private void validate(UnencryptedUser unencryptedUser) {

		LOGGER.info("Validating the user to be created.");

		if (unencryptedUser.getUserId().contains(" ")) {
			LOGGER.error("Validation failed: userId contains spaces");
			throw new InvalidUserIdException("User ID cannot contain spaces");
		}

		if (userProvider.exists(unencryptedUser.getUserId())) {
			LOGGER.error("Validation failed: userId already taken");
			throw new UserAlreadyExistException("User ID is already taken.");
		}

		LOGGER.info("Validation succeeded");
	}

}
