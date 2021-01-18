package com.theWheel.projects.YouShopPretty;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.theWheel.projects.YouShopPretty.Repository.ProductRepository;

@RequestScoped
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
	ProductRepository productRepository = new ProductRepository();
	
	@GET
	public Response getAllProducts() {	
		return Response.ok(productRepository.getAllProducts()).build();
	}
}
