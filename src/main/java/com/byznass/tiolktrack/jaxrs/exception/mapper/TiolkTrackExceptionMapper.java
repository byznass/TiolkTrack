package com.byznass.tiolktrack.jaxrs.exception.mapper;

import com.byznass.tiolktrack.kernel.TiolkTrackException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class TiolkTrackExceptionMapper implements ExceptionMapper<TiolkTrackException> {

	private static final String RESPONSE = "{\n\t\"error\": \"%s\"\n}";

	@Override
	public Response toResponse(TiolkTrackException exception) {

		return Response.status(INTERNAL_SERVER_ERROR)
				.type(APPLICATION_JSON_TYPE)
				.entity(String.format(RESPONSE, exception.getMessage()))
				.build();
	}
}
