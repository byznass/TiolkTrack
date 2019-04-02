package com.byznass.tiolktrack.jaxrs.resource.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

	@Override
	public Response toResponse(RuntimeException exception) {

		return Response.status(NOT_FOUND)
				.type(APPLICATION_JSON_TYPE)
				.entity("An error occured " + exception.getMessage())
				.build();
	}
}
