package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsPersister;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import com.byznass.tiolktrack.kernel.util.IdentifierValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static com.byznass.tiolktrack.kernel.util.IdentifierValidator.CHAR_SET;
import static com.byznass.tiolktrack.kernel.util.IdentifierValidator.MAX_LENGTH;

public class PersistGpsHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistGpsHandler.class);

	private final UserProvider userProvider;
	private final GpsPersister gpsPersister;

	@Inject
	public PersistGpsHandler(UserProvider userProvider, GpsPersister gpsPersister) {

		this.userProvider = userProvider;
		this.gpsPersister = gpsPersister;
	}

	public Gps persistGps(Gps gps) {

		LOGGER.info("Creating new GPS entity (\'{},{}\').", gps.getUserId(), gps.getName());
		validate(gps);
		gpsPersister.persist(gps);
		LOGGER.info("Successfully created new GPS entity (\'{},{}\').", gps.getUserId(), gps.getName());

		return gps;
	}

	private void validate(Gps gps) {

		LOGGER.info("Validating new GPS before persisting.");

		if (!userProvider.exists(gps.getUserId())) {
			LOGGER.error("Failed validation of new GPS. Non-existent GPS userId \'{}\'", gps.getUserId());
			throw new NoUserWithSuchIdException(gps.getUserId());
		}

		if (!IdentifierValidator.validate(gps.getName())) {
			LOGGER.error("Failed validation of new GPS. Invalid GPS name \'{}\'", gps.getName());
			throw new InvalidIdentifierException(String.format("Invalid GPS name. It can only contain maximum number of %s %s.", MAX_LENGTH, CHAR_SET));
		}

		LOGGER.info("Validation of new GPS succeeded.");
	}

}
