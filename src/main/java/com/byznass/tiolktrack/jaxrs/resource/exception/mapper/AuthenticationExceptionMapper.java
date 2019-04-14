package com.byznass.tiolktrack.jaxrs.resource.exception.mapper;

import com.byznass.tiolktrack.jaxrs.filter.AuthenticationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

	private static final String RESPONSE = "{\n\t\"error\": \"%s\"\n}";

	@Override
	public Response toResponse(AuthenticationException exception) {

		return Response.status(UNAUTHORIZED)
				.type(APPLICATION_JSON_TYPE)
				.entity(String.format(RESPONSE, exception.getMessage()))
				.build();
	}
}
