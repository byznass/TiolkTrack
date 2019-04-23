package com.byznass.tiolktrack.jaxrs.exception.mapper;

import com.byznass.tiolktrack.kernel.handler.InvalidUserIdException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class InvalidUserIdExceptionMapper implements ExceptionMapper<InvalidUserIdException> {

	private static final String RESPONSE = "{\n\t\"error\": \"%s\"\n}";

	@Override
	public Response toResponse(InvalidUserIdException exception) {

		return Response.status(BAD_REQUEST)
				.type(APPLICATION_JSON_TYPE)
				.entity(String.format(RESPONSE, exception.getMessage()))
				.build();
	}
}
