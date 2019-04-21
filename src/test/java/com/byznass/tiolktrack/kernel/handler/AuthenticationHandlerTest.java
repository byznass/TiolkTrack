package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.jaxrs.filter.AuthenticationException;
import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.crypto.TokenEncrypter;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthenticationHandlerTest {

	@Mock
	private UserProvider userProvider;
	@Mock
	private TokenEncrypter tokenEncrypter;

	private AuthenticationHandler handler;

	@Before
	public void setUp() {

		initMocks(this);

		handler = new AuthenticationHandler(userProvider, tokenEncrypter);
	}

	@Test(expected = NoUserWithSuchIdException.class)
	public void givenInvalidUserIdThenThrowException() {

		when(userProvider.getUser("ionel")).thenThrow(NoUserWithSuchIdException.class);

		handler.authenticate("ionel", "token");
	}

	@Test
	public void whenHashComputationFailedThenThrowException() {

		User user = new User("ionel", new byte[0], new byte[0]);
		when(userProvider.getUser("ionel")).thenReturn(user);
		when(tokenEncrypter.computeHash(eq("token"), aryEq(new byte[0]))).thenThrow(TiolkTrackException.class);

		try {
			handler.authenticate("ionel", "token");

			fail("Handler must throw an exception");
		} catch (TiolkTrackException e) {
			assertEquals("Authentication failed.", e.getMessage());
		}
	}

	@Test
	public void givenIncorrectTokenThenThrowException() {

		User user = new User("ionel", new byte[]{13, 15, 18}, new byte[0]);
		when(userProvider.getUser("ionel")).thenReturn(user);
		when(tokenEncrypter.computeHash(eq("token"), aryEq(new byte[0]))).thenReturn(new byte[]{13, 15, 17});

		try {
			handler.authenticate("ionel", "token");
			fail("Handler must throw an exception");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_TOKEN, e.getReason());
		}

	}

	@Test
	public void givenCorrectTokenThenSucceed() {

		User user = new User("ionel", new byte[]{13, 15, 17}, new byte[0]);
		when(userProvider.getUser("ionel")).thenReturn(user);
		when(tokenEncrypter.computeHash(eq("token"), aryEq(new byte[0]))).thenReturn(new byte[]{13, 15, 17});

		handler.authenticate("ionel", "token");
	}

}