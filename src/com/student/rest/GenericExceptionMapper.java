package com.student.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.student.model.AppError;

//need to register in web.xml provide its package in web.xml
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	public Response toResponse(Throwable ex) {
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new AppError("500", "Internal server error")).build();

	}
  
}
