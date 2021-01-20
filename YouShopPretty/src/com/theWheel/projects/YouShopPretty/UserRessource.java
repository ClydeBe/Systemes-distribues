package com.theWheel.projects.YouShopPretty;

import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import com.theWheel.projects.YouShopPretty.Entities.UserRole;
import com.theWheel.projects.YouShopPretty.Entities.UserRoleRepository;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserRessource {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_NAME = "Bearer ";
	
	UserRepository userRepository = new UserRepository();

	@RolesAllowed({"STAFF", "ADMIN"})
	@GET
	public Response AllUsers() {
		return Response.ok(userRepository.getAllUsers()).build();
	}

	@RolesAllowed({"STAFF", "ADMIN"})
	@GET
	@Path("{id}")
	public Response singleUserById(@PathParam("id") Long id) {
		User user;
		user = userRepository.findById(id);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	@PermitAll
	@GET
	@Path("validateUsername/{username}")
	public Response singleUserByUsername(@PathParam("username") String username) {
		User user;
		user = userRepository.findByUsername(username);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	@PermitAll
	@GET
	@Path("validateEmail/{email}")
	public Response singleUserByEmail(@PathParam("email") String email) {
		User user;
		user = userRepository.findByEmail(email);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	//signin and set JWT token
	@PermitAll
	@POST
	@Path("signin")
	public Response login(User u){
		User user = userRepository.signin(u);
		if(user != null) {
			String userRole;
			if(user.isSuperuser())
				userRole = "ADMIN";
			else {
				if(u.isStaff())
					userRole = "STAFF";
				else
					userRole = "CUSTOMER";
			}
			String token = issueToken(userRole, user.getUsername());
			return Response.ok().header(AUTHORIZATION_PROPERTY, AUTHENTICATION_NAME + token).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@PermitAll
	@POST
	public Response addUser(User u) {
		userRepository.createUser(u);
		if(userRepository.errors.isEmpty()) {
			//Succes de l'inscription, on attribue à l'utilisateur role et permission
			UserRoleRepository urr = new UserRoleRepository();
			User newUser = userRepository.findByUsername(u.getUsername());
			Long newUserId = newUser.getId();
			boolean isStaff = newUser.isStaff();
			boolean isSuperuser = newUser.isSuperuser();
			UserRole userRole = new UserRole();
			userRole.setUserId(newUserId);
			if(isSuperuser)
			{
				userRole.setRoleId(1L);
				urr.createUserRole(userRole);
			}
			else {
				if(isStaff)
				{
					userRole.setRoleId(2L);
					urr.createUserRole(userRole);
				}
				else {
					userRole.setRoleId(3L);
					urr.createUserRole(userRole);
				}
			}
			return Response.status(Status.CREATED).build();
		}
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@PUT
	public Response updateUser(User u) {
		userRepository.update(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") long id) {
		User u = new User();
		u.setId(id);
		userRepository.delete(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
    private String issueToken(String userRole, String userName) {
        final String secretKey = "a7e9s5/@q4<aUz45.45dqXd;";
        String jwtToken = Jwts.builder()
        		.setId(userName)
                .setSubject(userRole)
                .setIssuer("YouSHopPretty")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 29*60*1000L))
                .signWith(SignatureAlgorithm.HS512,secretKey.getBytes() )
                .compact();
        return jwtToken;
    }

}
