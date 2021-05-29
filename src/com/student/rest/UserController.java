package com.student.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.student.dao.UserDao;
import com.student.model.Message;
import com.student.model.User;

@Path("/api")
public class UserController {

	UserDao udao = new UserDao();

	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return udao.getAllUsers();
	}

	@GET
	@Path("/user/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUsersById(@PathParam("userid") int userid) {
		return udao.getUserById(userid);

	}

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	// JAXB annotation @XML root elemet only give class name as request body
	public Response addUser(User user) {
		return Response.status(201).entity(udao.addUser(user)).build();
	}

	@PUT
	@Path("/user/{userid}")
	@Consumes(MediaType.APPLICATION_JSON)
	// JAXB annotation @XML root elemet only give class name as request body
	public Response updateUser(@PathParam("userid") int userid, User user) {
		return Response.status(200).entity(udao.updateUser(user, userid)).build();
	}

	@POST
	@Path("/userLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// JAXB annotation @XML root elemet only give class name as request body
	public Response userLogin(User user) {

		return udao.userLogin(user) != null ? Response.status(200).entity(udao.userLogin(user)).build()
				: Response.status(401).entity("Unauthorize user").build();
	}

	@DELETE
	@Path("/user/{userid}")
	public Response deleteUser(@PathParam("userid") int userid) {

		return Response.status(200).entity(udao.deleteUser(userid)).build();
	}

	
}
