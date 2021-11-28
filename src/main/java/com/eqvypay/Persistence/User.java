package com.eqvypay.Persistence;

import java.util.UUID;

public class User {

	private UUID uuid;
	private String name;
	private String email;
	private String contact;
	private String password;
	
	// 
	
	public User() {
		this.uuid = UUID.randomUUID();
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
}
