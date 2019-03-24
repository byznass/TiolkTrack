package com.byznass.tiolktrack.resource.jaxrs.exception.mapper;

import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class NoGpsWIthIdExceptionMapper implements ExceptionMapper<NoGpsWithIdException> {

	private static final String RESPONSE = "{\n\t\"Error message\": \"%s\"\n}";

	@Inject
	public NoGpsWIthIdExceptionMapper() {

	}

	@Override
	public Response toResponse(NoGpsWithIdException exception) {

		return Response.status(NOT_FOUND)
				.type(APPLICATION_JSON_TYPE)
				.entity(String.format(RESPONSE, exception.getMessage()))
				.build();
	}
}
