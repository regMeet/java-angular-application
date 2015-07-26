package com.company.project.api.entities.google;

import com.company.project.api.entities.ProviderUser;
import com.google.gson.annotations.SerializedName;

public class GoogleUser implements ProviderUser {

	@SerializedName("sub")
	private String id;
	private String name;
	@SerializedName("given_name")
	private String firstname;
	@SerializedName("family_name")
	private String lastname;
	private String email;

	public GoogleUser() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
