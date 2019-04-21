package com.byznass.tiolktrack.kernel.crypto;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TokenEncrypter.class)
public class TokenEncrypterTest {

	private TokenEncrypter tokenEncrypter;

	@Before
	public void setUp() {

		mockStatic(MessageDigest.class);
		tokenEncrypter = new TokenEncrypter();
	}

	@Test
	public void givenNoImplementationForChosenHashAlgorithmThrowException() throws Exception {

		when(MessageDigest.getInstance("SHA-256")).thenThrow(NoSuchAlgorithmException.class);

		try {
			tokenEncrypter.computeHash("token", new byte[0]);

			fail("Encrypter must throw an exception");
		} catch (TiolkTrackException e) {
			assertEquals("No algorithm for chosen hash type", e.getMessage());
		}

	}

	@Test
	public void givenSuccessfulEncryptionThenReturnComputedHash() throws Exception {

		MessageDigest messageDigest = mock(MessageDigest.class);
		when(MessageDigest.getInstance("SHA-256")).thenReturn(messageDigest);
		when(messageDigest.digest(aryEq("token".getBytes()))).thenReturn(new byte[]{5, 12});

		byte[] passSalt = new byte[0];
		byte[] hash = tokenEncrypter.computeHash("token", passSalt);

		verify(messageDigest).update(passSalt);
		verify(messageDigest).digest("token".getBytes());

		assertArrayEquals(new byte[]{5, 12}, hash);
	}
}