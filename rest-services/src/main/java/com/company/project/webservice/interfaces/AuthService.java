package com.company.project.webservice.interfaces;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import com.company.project.persistence.entities.Users;
import com.company.project.webservice.implementations.AuthServiceImpl.Payload;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nimbusds.jose.JOSEException;

public interface AuthService {

	public abstract Response login(Users user, HttpServletRequest request) throws JOSEException;

	public abstract Response signup(Users user, HttpServletRequest request) throws JOSEException;

	public abstract Response loginFacebook(Payload payload, HttpServletRequest request) throws JsonParseException, JsonMappingException,
			IOException, ParseException, JOSEException;

	public abstract Response loginGoogle(Payload payload, HttpServletRequest request) throws JOSEException, ParseException,
			JsonParseException, JsonMappingException, IOException;

	public abstract Response unlink(String provider, HttpServletRequest request) throws ParseException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException;
}
