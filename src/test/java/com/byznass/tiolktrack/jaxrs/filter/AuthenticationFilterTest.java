package com.byznass.tiolktrack.jaxrs.filter;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.handler.AuthenticationHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.container.ContainerRequestContext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_AUTHENTICATION_METHOD;
import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_FORMAT;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthenticationFilterTest {

	@Mock
	private AuthenticationHandler authenticationHandler;
	private AuthenticationFilter filter;

	@Before
	public void setUp() {

		initMocks(this);

		filter = new AuthenticationFilter(authenticationHandler);
	}

	@Test
	public void givenNullAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn(null);

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_AUTHENTICATION_METHOD, e.getReason());
		}

	}

	@Test
	public void givenEmptyAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("");

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_AUTHENTICATION_METHOD, e.getReason());
		}

	}

	@Test
	public void givenNoBearerPrefixInAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("sdecfg,l");

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_AUTHENTICATION_METHOD, e.getReason());
		}

	}

	@Test
	public void givenNoSpaceAfterBearerPrefixInAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("Bearer_49695_oaie");

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_AUTHENTICATION_METHOD, e.getReason());
		}

	}

	@Test
	public void givenNoSpaceBetweenIdAndTokenInAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("Bearer _49695_oaie");

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_FORMAT, e.getReason());
		}

	}

	@Test
	public void givenEmptyIdAndTokenButASpaceInAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("Bearer   ");

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_FORMAT, e.getReason());
		}

	}

	@Test
	public void givenEmptyTokenInAuthorizationHeaderThenThrowException() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("Bearer userId ");

		try {
			filter.filter(request);
			fail("Authentication must fail when AUTHORIZATION header is empty.");
		} catch (AuthenticationException e) {
			assertEquals(INVALID_FORMAT, e.getReason());
		}
	}

	@Test
	public void givenTokenFromSpacesOnlyInAuthorizationHeaderThenSucceed() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("Bearer   userId  ");

		filter.filter(request);

		verify(authenticationHandler).authenticate("userId", " ");
	}

	@Test
	public void givenCorrectFormatThenSucceed() {

		ContainerRequestContext request = Mockito.mock(ContainerRequestContext.class);
		when(request.getHeaderString(AUTHORIZATION)).thenReturn("Bearer     userId   tok en ");

		filter.filter(request);

		verify(authenticationHandler).authenticate("userId", "  tok en ");
	}

	@Test
	public void test() {

		String paroli = "byznass";
		String salt = "pepeni murati";

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(salt.getBytes());

			byte[] hash = messageDigest.digest(paroli.getBytes());
			for(byte b : salt.getBytes()) {
				String s1 = String.format("%02x", b);
				System.out.print(s1);
			}

		} catch (NoSuchAlgorithmException e) {
			throw new TiolkTrackException("Authentication validation failed", e);
		}
	}


}