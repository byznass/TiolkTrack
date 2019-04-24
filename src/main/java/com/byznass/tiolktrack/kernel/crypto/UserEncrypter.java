package com.byznass.tiolktrack.kernel.crypto;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import com.byznass.tiolktrack.kernel.model.User;

import javax.inject.Inject;

public class UserEncrypter {

	private final SaltGenerator saltGenerator;
	private final TokenEncrypter tokenEncrypter;

	@Inject
	public UserEncrypter(SaltGenerator saltGenerator, TokenEncrypter tokenEncrypter) {

		this.saltGenerator = saltGenerator;
		this.tokenEncrypter = tokenEncrypter;
	}

	public User encrypt(UnencryptedUser unencryptedUser) {

		try {

			byte[] passSalt = saltGenerator.generateSalt();
			byte[] passHash = tokenEncrypter.computeHash(unencryptedUser.getPassword(), passSalt);

			return new User(unencryptedUser.getUserId(), passHash, passSalt);
		} catch (TiolkTrackException e) {
			throw new TiolkTrackException("Failed to encrypt user.", e);
		}
	}
}
