package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.jaxrs.filter.AuthenticationException;
import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.TokenEncrypter;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Arrays;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_TOKEN;

public class AuthenticationHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);

	private final UserProvider userProvider;
	private final TokenEncrypter tokenEncrypter;

	@Inject
	public AuthenticationHandler(UserProvider userProvider, TokenEncrypter tokenEncrypter) {

		this.userProvider = userProvider;
		this.tokenEncrypter = tokenEncrypter;
	}

	public void authenticate(String userId, String token) {

		try {
			User user = getUser(userId);

			byte[] candidatePassHash = tokenEncrypter.computeHash(token, user.getPassSalt());
			if (!Arrays.equals(user.getPassHash(), candidatePassHash)) {
				LOGGER.error("Hash values don't match.");
				throw new AuthenticationException(INVALID_TOKEN);
			}
			LOGGER.info("Authentication succeed.");
		} catch (TiolkTrackException e) {
			LOGGER.error("Failed encryption.", e);
			throw new TiolkTrackException("Authentication failed.", e);
		}
	}

	private User getUser(String userId) {

		try {
			return userProvider.getUser(userId);
		} catch (NoUserWithSuchIdException e) {
			LOGGER.error("Cannot find user with userId = {}", userId, e);
			throw new AuthenticationException(INVALID_TOKEN, e);
		}
	}
}
