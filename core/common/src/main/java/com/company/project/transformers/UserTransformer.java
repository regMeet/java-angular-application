package com.company.project.transformers;

import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.persistence.entities.User;

public class UserTransformer {

	public static User transformTo(AuthSignUpUserVO signUpUser) {
		User user = new User();

		user.setFirstname(signUpUser.getFirstname());
		user.setLastname(signUpUser.getLastname());
		user.setEmail(signUpUser.getEmail());
		user.setUsername(signUpUser.getUsername());
		user.setPassword(signUpUser.getPassword());

		return user;
	}

}
