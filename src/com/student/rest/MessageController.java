package com.student.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.student.dao.MessageDao;
import com.student.dao.UserDao;
import com.student.model.Message;

@Path("/api/msg")
public class MessageController {
	MessageDao mdao = new MessageDao();

	@GET
	@Path("/message/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages(@PathParam("userid") int userid) {

		return mdao.getAllmessages(userid);
	}

	@DELETE
	@Path("/message/{messageid}")
	public Response deleteMessage(@PathParam("messageid") int messageid) {

		return Response.status(200).entity(mdao.deleteMessage(messageid)).build();
	}
}
