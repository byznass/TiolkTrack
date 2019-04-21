package com.byznass.tiolktrack.jaxrs.filter;

import com.byznass.tiolktrack.kernel.handler.AuthenticationHandler;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_AUTHENTICATION_METHOD;
import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_FORMAT;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	private static final String AUTHENTICATION_SCHEME = "bearer ";
	private static final String ALL_LEADING_SPACES = "^ *";
	private static final String EMPTY = "";
	private static final String SPACE = " ";

	private final AuthenticationHandler authenticationHandler;

	@Inject
	public AuthenticationFilter(AuthenticationHandler authenticationHandler) {

		this.authenticationHandler = authenticationHandler;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws AuthenticationException {

		String authorizationHeader = requestContext.getHeaderString(AUTHORIZATION);
		validateAuthenticationMethod(authorizationHeader);

		String userIdWithToken = removeAuthMethod(authorizationHeader);
		validateUserToken(userIdWithToken);
	}

	private String removeAuthMethod(String authorizationHeader) {

		return authorizationHeader.substring(AUTHENTICATION_SCHEME.length());
	}

	private void validateAuthenticationMethod(String authorizationHeader) throws AuthenticationException {

		if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME)) {
			throw new AuthenticationException(INVALID_AUTHENTICATION_METHOD);
		}

	}

	private void validateUserToken(String userIdWithToken) throws AuthenticationException {

		userIdWithToken = userIdWithToken.replaceFirst(ALL_LEADING_SPACES, EMPTY);
		String[] splitUserIdWithToken = userIdWithToken.split(SPACE, -1);

		if (!userIdWithToken.contains(" ") || splitUserIdWithToken.length < 2) {
			throw new AuthenticationException(INVALID_FORMAT);
		}

		String userId = splitUserIdWithToken[0];
		String token = userIdWithToken.substring(userId.length() + 1);

		if (userId.isEmpty() || token.isEmpty()) {
			throw new AuthenticationException(INVALID_FORMAT);
		}

		authenticationHandler.authenticate(userId, token);
	}
}
