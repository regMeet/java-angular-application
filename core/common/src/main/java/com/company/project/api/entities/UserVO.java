package com.company.project.api.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserVO {

	@NotNull
	@Size(min = 2, max = 50, message="el error loco")
	// @Min(1)
	// @Max(20)
	String firstName;

	@NotNull
	// @Min(1)
	// @Max(20)
	String lastName;

	public UserVO() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UserVO [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
