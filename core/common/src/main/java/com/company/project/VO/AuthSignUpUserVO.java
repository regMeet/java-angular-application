package com.company.project.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class AuthSignUpUserVO {

	private String firstname;
	private String lastname;

	@NotNull(message = "signup.email.null")
	@Email(message = "signup.email.invalid")
	private String email;

	@NotNull(message = "signup.username.null")
	@Size(min = 4, max = 15, message = "signup.username.length")
	private String username;

	@NotNull(message = "signup.password.null")
	@Size(min = 4, max = 20, message = "signup.password.length")
	private String password;

	public AuthSignUpUserVO() {

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

}
