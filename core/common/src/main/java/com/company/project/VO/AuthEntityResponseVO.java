package com.company.project.VO;

import com.company.project.persistence.entities.User;

public class AuthEntityResponseVO {
	private final AuthUserVO user;
	private final String token;

	public AuthEntityResponseVO(User user, String token) {
		this.user = new AuthUserVO(user);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public AuthUserVO getUser() {
		return user;
	}

	public class AuthUserVO {
		private Long userId;
		private String currentUser;
		private String role;

		public AuthUserVO(Long userId, String currentUser, String role) {
			super();
			this.userId = userId;
			this.currentUser = currentUser;
			this.role = role;
		}

		public AuthUserVO(User user) {
			super();
			this.userId = user.getIdUser();
			this.currentUser = user.getUsername();
			this.role = user.getRole();
		}

		public Long getUserId() {
			return userId;
		}

		public String getCurrentUser() {
			return currentUser;
		}

		public String getRole() {
			return role;
		}

	}
}
