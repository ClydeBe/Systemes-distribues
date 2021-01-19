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
import javax.ws.rs.core.Response.Status;

import com.theWheel.projects.YouShopPretty.Entities.Whishlist;
import com.theWheel.projects.YouShopPretty.Repository.WhishListRepository;

@Path("whishList")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WhishListResource {
	
	WhishListRepository whishListRepository = new WhishListRepository();
	
	public WhishListResource() {
	}
	
	@GET
	public List<Whishlist> getAllWhishList() {
		return whishListRepository.getAllWhishList();
	}
	
	@GET
	@Path("{id}")
	public Response getwhishList(@PathParam("id") long id) {
		Whishlist whishList = whishListRepository.getWhishList(id);
		
		if(whishList == null) return Response.noContent().build();
		return Response.ok(whishList).build();
	}

	@POST
	public Response createWhisList(Whishlist whishList) {
		whishListRepository.createWhisList(whishList);
		if(whishListRepository.errors.isEmpty()) return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PUT
	public Response update(Whishlist whishList) {
		whishListRepository.updateWhisList(whishList);
		if(!whishListRepository.errors.isEmpty()) return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) {
		Whishlist whishList = new Whishlist();
		whishList.setId(id);
		whishListRepository.deleteWhisList(whishList);
		if(whishListRepository.errors.isEmpty()) return Response.status(Status.OK).build();
		return  Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	
}
