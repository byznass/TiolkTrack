package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.UserEncrypter;
import com.byznass.tiolktrack.kernel.dao.UserPersister;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.UnencryptedUser;
import com.byznass.tiolktrack.kernel.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersistUserHandlerTest {

	@Mock
	private UserPersister userPersister;
	@Mock
	private UserEncrypter userEncrypter;
	@Mock
	private UserProvider userProvider;

	private PersistUserHandler persistUserHandler;

	@Before
	public void setUp() {

		initMocks(this);
		persistUserHandler = new PersistUserHandler(userProvider, userEncrypter, userPersister);
	}

	@Test(expected = UserAlreadyExistException.class)
	public void givenExistingUserThenThrowException() {

		UnencryptedUser unencryptedUser = new UnencryptedUser("igor", "patan");
		when(userProvider.exists("igor")).thenReturn(true);

		persistUserHandler.persistUser(unencryptedUser);

		verify(userProvider).exists("igor");
	}

	@Test
	public void givenAnExceptionWhileValidatingUserIdThenThrowException() {

		UnencryptedUser unencryptedUser = new UnencryptedUser("igor", "patan");
		when(userProvider.exists("igor")).thenThrow(TiolkTrackException.class);

		try {
			persistUserHandler.persistUser(unencryptedUser);

			fail("Persister must fail.");
		} catch (TiolkTrackException e) {
			assertEquals("Failed to create new user.", e.getMessage());
		}
	}


	@Test
	public void givenNewUserThenPersist() {

		UnencryptedUser unencryptedUser = new UnencryptedUser("igor", "patan");
		User user = new User("igor", new byte[0], new byte[0]);

		when(userEncrypter.encrypt(unencryptedUser)).thenReturn(user);

		persistUserHandler.persistUser(unencryptedUser);

		verify(userPersister).persist(user);
	}
}