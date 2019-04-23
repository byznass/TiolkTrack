package com.byznass.tiolktrack.kernel.crypto;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import com.byznass.tiolktrack.kernel.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserEncrypterTest {

	private UserEncrypter userEncrypter;

	@Mock
	private SaltGenerator saltGenerator;
	@Mock
	private TokenEncrypter tokenEncrypter;

	@Before
	public void setUp() {

		initMocks(this);
		userEncrypter = new UserEncrypter(saltGenerator, tokenEncrypter);
	}

	@Test
	public void givenExceptionThenThrowException() {

		when(saltGenerator.getSalt()).thenThrow(TiolkTrackException.class);

		UnencryptedUser unencryptedUser = new UnencryptedUser("igor", "patan");

		try {
			userEncrypter.encrypt(unencryptedUser);
			fail("Encrypter must fail.");
		} catch (TiolkTrackException e) {
			assertEquals("Failed to encrypt user.", e.getMessage());
		}
	}

	@Test
	public void givenUnencryptedUserThenEncrypt() {

		when(saltGenerator.getSalt()).thenReturn(new byte[]{3, 4});
		when(tokenEncrypter.computeHash(eq("patan"), aryEq(new byte[]{3, 4}))).thenReturn(new byte[]{8, 9, 1});

		UnencryptedUser unencryptedUser = new UnencryptedUser("igor", "patan");

		User actualUser = userEncrypter.encrypt(unencryptedUser);

		User expectedUser = new User("igor", new byte[]{8, 9, 1}, new byte[]{3, 4});
		assertEquals(expectedUser, actualUser);
	}

}