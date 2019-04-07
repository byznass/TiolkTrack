package com.byznass.tiolktrack.jaxrs.resource.exception.mapper;

import com.byznass.tiolktrack.kernel.handler.NoLocationForGpsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class NoLocationForGpsExceptionMapper implements ExceptionMapper<NoLocationForGpsException> {

	private static final String RESPONSE = "{\n\t\"error\": \"%s\"\n}";

	@Override
	public Response toResponse(NoLocationForGpsException exception) {

		return Response.status(NOT_FOUND)
				.type(APPLICATION_JSON_TYPE)
				.entity(String.format(RESPONSE, exception.getMessage()))
				.build();
	}
}
