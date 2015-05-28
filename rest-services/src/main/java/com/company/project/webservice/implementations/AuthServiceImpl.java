package com.company.project.webservice.implementations;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.hibernate.validator.constraints.NotBlank;

import com.company.project.persistence.entities.Users;
import com.company.project.webservice.interfaces.AuthService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nimbusds.jose.JOSEException;

public class AuthServiceImpl implements AuthService {

	@Override
	public Response login(Users user, HttpServletRequest request) throws JOSEException {
		// TODO Auto-generated method stub
		return null;
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
