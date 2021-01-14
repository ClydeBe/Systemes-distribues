package main.java.com.theWheel.projects.YouShopPretty.Entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String username;
	private String password;
	private String email;
	private String lastname;
	private String firstname;
	
	@Column(name = "date_joined")
	private Date dateJoined;
	@Column(name = "is_active")
	private boolean isActive;
	@Column(name = "is_staff")
	private boolean isStaff;
	@Column(name = "is_superuser")
	private boolean isSuperuser;

	public User() {
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public Date getDateJoined() {
		return dateJoined;
	}


	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public boolean isStaff() {
		return isStaff;
	}


	public void setStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}


	public boolean isSuperuser() {
		return isSuperuser;
	}


	public void setSuperuser(boolean isSuperuser) {
		this.isSuperuser = isSuperuser;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
