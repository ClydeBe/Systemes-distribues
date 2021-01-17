package com.theWheel.projects.YouShopPretty;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.theWheel.projects.YouShopPretty.Entities.User;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

@RequestScoped
@Path("myresource")
@Produces(MediaType.APPLICATION_JSON)
public class hmmm {
	
	private UserRepository ur = new UserRepository();

    @GET
    public List<User> http() {
		return ur.getAllUsers();
	}
	
    @GET
    @Path("{id}")
    public Response singleUser(@PathParam("id") Long id) {
    	User user;
    	user = ur.findById(id);
    	if(user == null)
    		return Response.noContent().build();
    	return Response.ok(user).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
    	ur.create(u);
    	return Response.ok().build();
    }

}
