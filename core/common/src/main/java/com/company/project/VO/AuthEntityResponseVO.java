package com.company.project.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.company.project.persistence.entities.User;

@Data
public class AuthEntityResponseVO {
	private final AuthUserVO user;
	private final String token;

	public AuthEntityResponseVO(User user, String token) {
		this.user = new AuthUserVO(user);
		this.token = token;
	}

	@Data
	@AllArgsConstructor
	public class AuthUserVO {
		private Long userId;
		private String currentUser;
		private String role;

		public AuthUserVO(User user) {
			super();
			this.userId = user.getIdUser();
			this.currentUser = user.getUsername();
			this.role = user.getRole();
		}

	}
}
