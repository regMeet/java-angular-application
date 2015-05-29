package com.company.project.webservice.implementations;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.project.Auth.AuthUtils;
import com.company.project.Auth.ErrorMessage;
import com.company.project.Auth.PasswordService;
import com.company.project.Auth.Token;
import com.company.project.persistence.entities.Users;
import com.company.project.services.interfaces.UserService;
import com.company.project.webservice.interfaces.AuthService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

@RequestMapping("/auth")
public class AuthServiceImpl implements AuthService {

	private UserService userService;

	public static final String CLIENT_ID_KEY = "client_id";
	public static final String REDIRECT_URI_KEY = "redirect_uri";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CODE_KEY = "code";
	public static final String GRANT_TYPE_KEY = "grant_type";
	public static final String AUTH_CODE = "authorization_code";

	public static final String CONFLICT_MSG = "There is already a %s account that belongs to you";
	public static final String NOT_FOUND_MSG = "User not found";
	public static final String LOGING_ERROR_MSG = "Wrong email and/or password";
	public static final String UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method";

	public static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	public AuthServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@Override
	public Response login(Users user, HttpServletRequest request) throws JOSEException {
//		final Optional<Users> foundUser = userService.findByEmail(user.getEmail());
//		if (foundUser.isPresent() && PasswordService.checkPassword(user.getPassword(), foundUser.get().getPassword())) {
//			final Token token = AuthUtils.createToken(request.getRemoteHost(), foundUser.get().getIdUser());
//			return Response.ok().entity(token).build();
//		}
		System.out.println("loginnnnnn");
		return Response.status(Status.UNAUTHORIZED).entity(new ErrorMessage(LOGING_ERROR_MSG)).build();
	}

	@Override
	public Response signup(Users user, HttpServletRequest request) throws JOSEException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loginFacebook(Payload payload, HttpServletRequest request) throws JsonParseException, JsonMappingException,
			IOException, ParseException, JOSEException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loginGoogle(Payload payload, HttpServletRequest request) throws JOSEException, ParseException, JsonParseException,
			JsonMappingException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loginLinkedin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loginGithub() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loginFoursquare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loginTwitter(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response unlink(String provider, HttpServletRequest request) throws ParseException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Inner classes for entity wrappers
	 */
	public static class Payload {
		@NotBlank
		String clientId;

		@NotBlank
		String redirectUri;

		@NotBlank
		String code;

		public String getClientId() {
			return clientId;
		}

		public String getRedirectUri() {
			return redirectUri;
		}

		public String getCode() {
			return code;
		}
	}
}
