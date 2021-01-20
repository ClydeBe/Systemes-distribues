package com.theWheel.projects.YouShopPretty;

import java.util.List;

import javax.annotation.security.RolesAllowed;
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
import com.theWheel.projects.YouShopPretty.Repository.BillRepository;

@Path("bill")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResource {

	BillRepository billRepository = new BillRepository();
	
	@RolesAllowed({"STAFF", "ADMIN"})
	@GET
	public Response getAllBills() {	
		return Response.ok(billRepository.getAllBills()).build();
	}
	
	@RolesAllowed({"STAFF", "ADMIN"})
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id") Long id) {
		Bill b = billRepository.getById(id);
		if(b != null)
			return Response.ok(b).build();
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@GET
	@Path("user/{id}")
	public Response getByUserId(@PathParam("id") Long id) {
		List<Bill> b = billRepository.getByUserId(id);
		if(b != null)
			return Response.ok(b).build();
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@POST
	public Response createProduct(Bill b) {
		billRepository.create(b);
		if(billRepository.errors.isEmpty())
			return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(billRepository.errors).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@PUT
	public Response editProduct(Bill b) {
		billRepository.update(b);
		if(billRepository.errors.isEmpty())
			return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(billRepository.errors).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@DELETE
	@Path("{id}")
	public Response deleteProduct(Bill b) {
		billRepository.delete(b);
		if(billRepository.errors.isEmpty())
			return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(billRepository.errors).build();
	}
}
