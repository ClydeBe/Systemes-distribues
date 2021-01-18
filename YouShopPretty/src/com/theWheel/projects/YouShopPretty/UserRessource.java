package com.theWheel.projects.YouShopPretty;

import java.util.List;

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

import com.theWheel.projects.YouShopPretty.Entities.User;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

@Path("Account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserRessource {

	UserRepository userRepository = new UserRepository();

//	public UserRessource() {}

	@GET
	public List<User> AllUsers() {
		return userRepository.getAllUsers();
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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User u) {
		userRepository.createUser(u);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User u) {
		userRepository.update(u);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") long id) {
		User u = new User();
		u.setId(id);
		userRepository.delete(u);
		return Response.ok().build();

	}

}
