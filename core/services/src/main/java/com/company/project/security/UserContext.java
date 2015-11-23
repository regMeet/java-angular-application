package com.company.project.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.company.project.persistence.entities.User;

/** This object wraps {@link User} and makes it {@link UserDetails} so that Spring Security can use it. */
public class UserContext implements UserDetails {
	private static final long serialVersionUID = 8164544376697651692L;

	private User user;

	public UserContext(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		String role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role));

		// for (String role : user.getRoles()) {
		// authorities.add(new SimpleGrantedAuthority(role));
		// }

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO: make credentials expire
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		return this == o || o != null && o instanceof UserContext && Objects.equals(user, ((UserContext) o).user);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(user);
	}

	@Override
	public String toString() {
		return "UserContext{" + "user=" + user + '}';
	}

	public Long getId() {
		return user.getIdUser();
	}

	public String getRole() {
		return user.getRole();
	}

}
