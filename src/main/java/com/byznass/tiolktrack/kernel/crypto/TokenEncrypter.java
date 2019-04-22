package com.byznass.tiolktrack.kernel.crypto;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenEncrypter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenEncrypter.class);

	public byte[] computeHash(String token, byte[] passSalt) {

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(passSalt);

			return messageDigest.digest(token.getBytes());

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No algorithm for chosen hash type", e);
			throw new TiolkTrackException("No algorithm for chosen hash type", e);
		}
	}

}
