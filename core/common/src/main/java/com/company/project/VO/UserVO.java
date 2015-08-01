package com.company.project.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserVO {

	private Long idUser;
	@NotNull
	@Size(min = 2, max = 50, message = "user.null")
	String firstname;
	@NotNull
	String lastname;
	private String email;
	private String facebook;
	private String google;
	private String role;
	private String username;
	private String telephone;
	private String mobile;

	public UserVO() {
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
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

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "UserVO [idUser=" + idUser + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", facebook=" + facebook
				+ ", google=" + google + ", role=" + role + ", username=" + username + ", telephone=" + telephone + ", mobile=" + mobile + "]";
	}

}
