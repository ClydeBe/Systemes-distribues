package com.theWheel.projects.YouShopPretty;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

import main.java.com.theWheel.projects.YouShopPretty.Entities.User;

@Path("myresource")
@Produces(MediaType.APPLICATION_JSON)
public class hmmm {
	
	
	@Path("not")
    @GET
    public List<User> http() {
		return UserRepository.getAllUsers();
	}

}
