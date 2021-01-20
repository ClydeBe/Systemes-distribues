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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.theWheel.projects.YouShopPretty.Entities.Product;
import com.theWheel.projects.YouShopPretty.Repository.ProductRepository;

@RequestScoped
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
	
	ProductRepository productRepository = new ProductRepository();
	
	@PermitAll
	@GET
	public Response getAllProducts() {	
		return Response.ok(productRepository.getAllProducts()).build();
	}
	
	@PermitAll
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id") Long id) {
		Product p = productRepository.getById(id);
		if(p != null)
			return Response.ok(p).build();
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PermitAll
	@GET
	@Path("search/{query}")
	public Response search(@PathParam("query") String query) {
		List<Product> p = productRepository.getByName(query);
		if(p != null)
			return Response.ok(p).build();
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PermitAll
	@GET
	@Path("advancedsearch/{query}")
	public Response advanceSearch(@PathParam("query") String query,@QueryParam("min") double min,
			@QueryParam("max") double max) {
		List<Product> p = productRepository.advanceQuery(query, min, max);
		if(p != null)
			return Response.ok(p).build();
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@RolesAllowed({"STAFF", "ADMIN"})
	@POST
	public Response createProduct(Product p) {
		productRepository.create(p);
		if(productRepository.errors.isEmpty())
			return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(productRepository.errors).build();
	}
	
	@RolesAllowed({"STAFF", "ADMIN"})
	@PUT
	public Response editProduct(Product p) {
		productRepository.update(p);
		if(productRepository.errors.isEmpty())
			return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(productRepository.errors).build();
	}
	
	@RolesAllowed({"STAFF", "ADMIN"})
	@DELETE
	@Path("{id}")
	public Response deleteProduct(Product p) {
		productRepository.delete(p);
		if(productRepository.errors.isEmpty())
			return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(productRepository.errors).build();
	}
}
