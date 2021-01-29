package com.theWheel.projects.YouShopPretty.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_permission", schema = "public")
public class RolePermission implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue()
	@Column(name = "id_user_permission")
	private Long Id;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "permission_id")
	private Long permissionId;

	public RolePermission() {
		super();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

}
