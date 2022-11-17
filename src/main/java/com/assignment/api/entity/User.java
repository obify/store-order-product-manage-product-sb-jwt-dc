package com.assignment.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblusers")
public class User extends BaseEntity<String>{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@Id
	private int id;

	@Column(name="fullName")
	private String fullname;

	@Column(name="mobileNo")
	private String phoneno;

	@Column(name="email")
	private String email;


	@Column(name = "userName")
	private String userName;

	@Column(name = "password")
	private String password;


	@Column(name = "userRole")
	private String authority;

	@Column(name="status")
	private boolean status;

	public User() {
		super();
	}

	public User(int id, String fullname, String phoneno, boolean status) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.phoneno = phoneno;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	@JsonIgnore
	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}


	public boolean isStatus() {
		return status;
	}


	@JsonIgnore
	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getAuthority() {
		return authority;
	}

	@JsonIgnore
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}





}
