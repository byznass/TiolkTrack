package com.byznass.tiolktrack.kernel.crypto;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenEncrypter.class);

	public byte[] generateSalt() throws TiolkTrackException {

		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[32];
			secureRandom.nextBytes(salt);

			return salt;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No algorithm for chosen hash type", e);
			throw new TiolkTrackException("No algorithm for chosen hash type", e);
		}
	}
}
