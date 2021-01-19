package com.theWheel.projects.YouShopPretty;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.theWheel.projects.YouShopPretty.Entities.Permission;
import com.theWheel.projects.YouShopPretty.Entities.Role;
import com.theWheel.projects.YouShopPretty.Entities.UserPermission;
import com.theWheel.projects.YouShopPretty.Entities.UserRole;
import com.theWheel.projects.YouShopPretty.Entities.UserRoleRepository;
import com.theWheel.projects.YouShopPretty.Repository.PermissionRepository;
import com.theWheel.projects.YouShopPretty.Repository.RolePermissionRepository;
import com.theWheel.projects.YouShopPretty.Repository.RoleRepository;
import com.theWheel.projects.YouShopPretty.Repository.UserPermissionRepository;


@Path("admin")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {
	
	RoleRepository roleRepository   		            = new RoleRepository();
	PermissionRepository permissionRepository           = new PermissionRepository();
	UserPermissionRepository userPermissionRepository   = new UserPermissionRepository();
	RolePermissionRepository rolePermissionRepository   = new RolePermissionRepository();
	UserRoleRepository userRoleRepository 			    = new UserRoleRepository();

	
	public AdminResource() {}

	/* Requette GET */

	@GET
	public Response getAllRole() {
		List<Role> roles = roleRepository.getAllRoles();
		if(roles.isEmpty()) return Response.noContent().build();
		return Response.ok(roles).build();
	}
	
	@GET
	public Response getAllpermission() {
		List<Permission> permission = permissionRepository.getAllPermissions();
		if(permission.isEmpty()) return Response.noContent().build();
		return Response.ok(permission).build();
	}
	
	@GET
	public Response getAllUserPermission() {
		List<UserPermission> userPermission = userPermissionRepository.getAllUserPermission();
		if(userPermission.isEmpty()) return Response.noContent().build();
		return Response.ok(userPermission).build();
	}
	

	
	@GET
	public Response getAllUserRole() {
		List<UserRole> userRole = userRoleRepository.getAllUserRole();
		if(userRole.isEmpty()) return Response.noContent().build();
		return Response.ok(userRole).build();
	}
	
	/* Requette POST */
	
	@POST
	public Response createRole(Role role) {
		roleRepository.createRole(role);
		if(roleRepository.errors.isEmpty()) return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(roleRepository.errors).build();
	}
	
	@POST
	public Response createPermission(Permission permission) {
		permissionRepository.createPermission(permission);
		if(permissionRepository.errors.isEmpty()) return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(permissionRepository.errors).build();
	}
	
	@POST
	public Response createUserPermission(UserPermission userPermission) {
		userPermissionRepository.createUserPermission(userPermission);
		if(userPermissionRepository.errors.isEmpty()) return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(userPermissionRepository.errors).build();
	}
	

	@POST
	public Response createUserRole(UserRole userRole) {
		userRoleRepository.createUserRole(userRole);
		if(userRoleRepository.errors.isEmpty()) return Response.status(Status.CREATED).build();
		return Response.status(Status.EXPECTATION_FAILED).entity(userRoleRepository.errors).build();
	}
	
	/* Requette PUT */
	
	@PUT
	public Response updateRole(Role role) {
		roleRepository.updateRole(role);
		if(roleRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(roleRepository.errors).build();
	}
	
	@PUT
	public Response updatePermission(Permission permission) {
		permissionRepository.updatePermission(permission);
		if(permissionRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(permissionRepository.errors).build();
	}
	
	@PUT
	public Response updateUserPermission(UserPermission userPermission) {
		userPermissionRepository.updateUserPermission(userPermission);
		if(userPermissionRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(userPermissionRepository.errors).build();
	}
	

	@PUT
	public Response updateUserRole(UserRole userRole) {
		userRoleRepository.updateUserRole(userRole);
		if(userRoleRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(userRoleRepository.errors).build();
	}
	
	/* Requette DELETE */
	
	@DELETE
	@Path("deleteRole/{id}")
	public Response deleteRole(@PathParam("id") long id) {
		Role role = new Role();
		role.setId(id);
		roleRepository.updateRole(role);
		if(roleRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(roleRepository.errors).build();
	}
	
	@DELETE
	@Path("deletePermission/{id}")
	public Response deletePermission(@PathParam("id") long id) {
		Permission permission = new Permission();
		permission.setId(id);
		permissionRepository.deletePermission(permission);
		if(permissionRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(permissionRepository.errors).build();
	}
	
	@DELETE
	@Path("deleteUserPermission/{id}")
	public Response deleteUserPermission(@PathParam("id") long id) {
		UserPermission userPermission = new UserPermission();
		userPermission.setId(id);
		userPermissionRepository.deleteUserPermission(userPermission);
		if(userPermissionRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(userPermissionRepository.errors).build();
	}
	

	@DELETE
	@Path("deleteUserRole/{id}")
	public Response deleteUserRole(@PathParam("id") long id) {
		UserRole userRole = new UserRole();
		userRole.setId(id);
		userRoleRepository.deleteUserRole(userRole);
		if(userRoleRepository.errors.isEmpty()) return Response.ok().build();
		return Response.status(Status.EXPECTATION_FAILED).entity(userRoleRepository.errors).build();
	}
}
