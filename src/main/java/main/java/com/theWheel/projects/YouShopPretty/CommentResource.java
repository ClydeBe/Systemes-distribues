package com.theWheel.projects.YouShopPretty;

import java.util.List;

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

import com.theWheel.projects.YouShopPretty.Entities.Bill;
import com.theWheel.projects.YouShopPretty.Entities.Comment;
import com.theWheel.projects.YouShopPretty.Repository.CommentRepository;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

@RequestScoped
@Path("comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	CommentRepository commentRepository = new CommentRepository();
	
	@GET
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@Path("user/{Id}")
	public Response getByUserId(@PathParam("Id") Long Id) {
		List<Comment> comments = commentRepository.getByUserId(Id);
		if (commentRepository.errors.isEmpty())
			return Response.ok(comments).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PermitAll
	@GET
	@Path("product/{Id}")
	public Response getByProductId(@PathParam("Id") Long Id) {
		List<Comment> comments = commentRepository.getByUserId(Id);
		if (commentRepository.errors.isEmpty())
			return Response.ok(comments).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@POST
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	public Response create(Comment c) {
		commentRepository.addComment(c);
		if(commentRepository.errors.isEmpty())
			return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PUT
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	public Response editProduct(Comment c) {
		commentRepository.update(c);
		if(commentRepository.errors.isEmpty())
			return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(commentRepository.errors).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@DELETE
	@Path("{id}")
	public Response deleteProduct(Comment c) {
		commentRepository.delete(c);
		if(commentRepository.errors.isEmpty())
			return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(commentRepository.errors).build();
	}
}
