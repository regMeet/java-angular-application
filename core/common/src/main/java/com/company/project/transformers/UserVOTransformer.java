package com.company.project.transformers;

import com.company.project.VO.UserVO;
import com.company.project.persistence.entities.User;

public class UserVOTransformer {

	public static UserVO transformTo(User userToUnlink) {
		UserVO userVO = new UserVO();

		userVO.setIdUser(userToUnlink.getIdUser());
		userVO.setFirstname(userToUnlink.getFirstname());
		userVO.setLastname(userToUnlink.getLastname());
		userVO.setEmail(userToUnlink.getEmail());
		userVO.setFacebook(userToUnlink.getFacebook());
		userVO.setGoogle(userToUnlink.getGoogle());
		userVO.setRole(userToUnlink.getRole());
		userVO.setUsername(userToUnlink.getUsername());
		userVO.setTelephone(userToUnlink.getTelephone());
		userVO.setMobile(userToUnlink.getMobile());

		return userVO;
	}

}
