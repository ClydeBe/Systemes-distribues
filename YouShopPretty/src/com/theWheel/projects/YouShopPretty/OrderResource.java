package com.theWheel.projects.YouShopPretty;

import java.util.List;

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

import com.theWheel.projects.YouShopPretty.Entities.Order;
import com.theWheel.projects.YouShopPretty.Repository.OrderRepository;

@RequestScoped
@Path("order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

	OrderRepository orderRepository = new OrderRepository();
	
	@GET
	@RolesAllowed({"ADMIN", "STAFF"})
	public Response getAllOrders() {
		return Response.status(Status.OK).entity(orderRepository.getAllOrders()).build();
	}
	
	@GET
	@RolesAllowed({"ADMIN", "STAFF"})
	@Path("{Id}")
	public Response getById(@PathParam("Id") Long Id) {
		return Response.ok(orderRepository.getById(Id)).build();
	}
	
	@GET
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@Path("user/{username}")
	public Response getByUsername(@PathParam("username") String username) {
		List<Order> orders = orderRepository.getByUsername(username);
		if(orders == null || orders.size() == 0)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.OK).entity(orderRepository.getByUserId(null)).build();
	}
	
	@POST
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	public Response createOrder(Order order) {
		boolean isCreated = orderRepository.create(order);
		if(!isCreated)
			return Response.status(Status.EXPECTATION_FAILED).build();
		return Response.status(Status.CREATED).build();
	}
	
	@PUT
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	public Response modifyOrder(Order order) {
		boolean isUpdated = orderRepository.update(order);
		if(!isUpdated)
			return Response.status(Status.EXPECTATION_FAILED).build();
		return Response.status(Status.OK).build();
	}
	
	@DELETE
	@RolesAllowed({"STAFF", "ADMIN"})
	public Response deleteOrder(Order order) {
		boolean isDeleted = orderRepository.delete(order);
		if(!isDeleted)
			return Response.status(Status.EXPECTATION_FAILED).build();
		return Response.status(Status.OK).build();
	}
}
