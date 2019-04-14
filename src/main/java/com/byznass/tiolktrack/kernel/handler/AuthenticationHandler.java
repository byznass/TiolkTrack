package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.jaxrs.filter.AuthenticationException;
import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_TOKEN;

public class AuthenticationHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);

	private final UserProvider userProvider;

	@Inject
	public AuthenticationHandler(UserProvider userProvider) {

		this.userProvider = userProvider;
	}

	public void authenticate(String userId, String token) {

		User user = userProvider.getUser(userId);

		byte[] candidatePassHash = computeHash(token, user.getPassSalt());

		if (!Arrays.equals(user.getPassHash(), candidatePassHash)) {
			throw new AuthenticationException(INVALID_TOKEN);
		}
	}

	private byte[] computeHash(String token, byte[] passSalt) {

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(passSalt);

			return messageDigest.digest(token.getBytes());

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No algorithm for chosen hash type", e);
			throw new TiolkTrackException("Authentication validation failed", e);
		}
	}
}
