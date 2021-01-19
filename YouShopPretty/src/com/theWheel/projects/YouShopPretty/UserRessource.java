package com.theWheel.projects.YouShopPretty;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.theWheel.projects.YouShopPretty.Entities.User;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserRessource {

	UserRepository userRepository = new UserRepository();

//	public UserRessource() {}

	@GET
	public Response AllUsers() {
		return Response.ok(userRepository.getAllUsers()).build();
	}

	@GET
	@Path("{id}")
	public Response singleUserById(@PathParam("id") Long id) {
		User user;
		user = userRepository.findById(id);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	@GET
	@Path("validateUsername/{username}")
	public Response singleUserByUsername(@PathParam("username") String username) {
		User user;
		user = userRepository.findByUsername(username);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	@GET
	@Path("validateEmail/{email}")
	public Response singleUserByEmail(@PathParam("email") String email) {
		User user;
		user = userRepository.findByEmail(email);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	//signin
	
	@POST
	@Path("signin")
	public Response login(User u){
		boolean correctCredentials = userRepository.signin(u);
		if(correctCredentials)
			return Response.ok().build();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	

	@POST
	public Response addUser(User u) {
		userRepository.createUser(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PUT
	public Response updateUser(User u) {
		userRepository.update(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();	}
	
	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") long id) {
		User u = new User();
		u.setId(id);
		userRepository.delete(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}

}
