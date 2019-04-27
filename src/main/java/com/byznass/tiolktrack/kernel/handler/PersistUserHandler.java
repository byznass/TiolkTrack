package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.UserEncrypter;
import com.byznass.tiolktrack.kernel.dao.UserPersister;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import com.byznass.tiolktrack.kernel.model.User;
import com.byznass.tiolktrack.kernel.util.IdentifierValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static com.byznass.tiolktrack.kernel.util.IdentifierValidator.CHAR_SET;
import static com.byznass.tiolktrack.kernel.util.IdentifierValidator.MAX_LENGTH;

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

		if (!IdentifierValidator.validate(unencryptedUser.getUserId())) {
			LOGGER.error("Failed validation of new User. Invalid user \'{}\'", unencryptedUser.getUserId());
			throw new InvalidIdentifierException(String.format("Invalid User ID. It can only contain maximum number of %s %s.", MAX_LENGTH, CHAR_SET));
		}

		if (userProvider.exists(unencryptedUser.getUserId())) {
			LOGGER.error("Validation failed: userId already taken");
			throw new UserAlreadyExistException("User ID is already taken.");
		}

		LOGGER.info("Validation succeeded");
	}

}
