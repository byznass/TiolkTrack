package com.byznass.tiolktrack.jaxrs.filter;

import com.byznass.tiolktrack.kernel.handler.AuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.Base64;

import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_AUTHENTICATION_METHOD;
import static com.byznass.tiolktrack.jaxrs.filter.AuthenticationException.Reason.INVALID_FORMAT;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

	private static final String AUTHENTICATION_SCHEME = "basic ";
	private static final String COLON = ":";

	private final AuthenticationHandler authenticationHandler;

	@Inject
	public AuthenticationFilter(AuthenticationHandler authenticationHandler) {

		this.authenticationHandler = authenticationHandler;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws AuthenticationException {

		LOGGER.info("Starting request authentication");

		String authorizationHeader = requestContext.getHeaderString(AUTHORIZATION);
		validateAuthenticationMethod(authorizationHeader);

		String userIdWithToken = removeAuthMethod(authorizationHeader);
		validateUserToken(userIdWithToken);
	}

	private void validateAuthenticationMethod(String authorizationHeader) throws AuthenticationException {

		if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME)) {
			LOGGER.error("Failed authentication: invalid authentication method: '{}'", authorizationHeader);
			throw new AuthenticationException(INVALID_AUTHENTICATION_METHOD);
		}
	}

	private String removeAuthMethod(String authorizationHeader) {

		return authorizationHeader.substring(AUTHENTICATION_SCHEME.length());
	}

	private void validateUserToken(String userIdWithToken) throws AuthenticationException {

		try {
			Base64.Decoder decoder = Base64.getDecoder();
			String decodedPair = new String(decoder.decode(userIdWithToken));

			String[] credentials = decodedPair.split(COLON, -1);

			if (credentials.length < 2) {
				LOGGER.error("Failed authentication: invalid format");
				throw new AuthenticationException(INVALID_FORMAT);
			}

			String userId = credentials[0];
			authenticationHandler.authenticate(userId, decodedPair.substring(userId.length() + 1));
			LOGGER.info("Finished authentication: success");
		} catch (IllegalArgumentException e) {
			LOGGER.error("Failed authentication: invalid Base64 string");
			throw new AuthenticationException(INVALID_FORMAT);
		}
	}
}
