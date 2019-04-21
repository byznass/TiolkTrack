package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.jaxrs.filter.AuthenticationException;
import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.TokenEncrypter;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.User;

import javax.inject.Inject;
import java.util.Arrays;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_TOKEN;

public class AuthenticationHandler {

	private final UserProvider userProvider;
	private final TokenEncrypter tokenEncrypter;

	@Inject
	public AuthenticationHandler(UserProvider userProvider, TokenEncrypter tokenEncrypter) {

		this.userProvider = userProvider;
		this.tokenEncrypter = tokenEncrypter;
	}

	public void authenticate(String userId, String token) {

		try {
			User user = userProvider.getUser(userId);

			byte[] candidatePassHash = tokenEncrypter.computeHash(token, user.getPassSalt());

			if (!Arrays.equals(user.getPassHash(), candidatePassHash)) {
				throw new AuthenticationException(INVALID_TOKEN);
			}
		} catch (TiolkTrackException e) {
			throw new TiolkTrackException("Authentication failed.", e);
		}
	}
}
