package com.company.project.webservice.interfaces;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.company.project.persistence.entities.User;
import com.company.project.webservice.implementations.AuthServiceImpl.Payload;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nimbusds.jose.JOSEException;

public interface AuthService {

	public abstract ResponseEntity login(User user, HttpServletRequest request) throws JOSEException;

	public abstract ResponseEntity signup(User user, HttpServletRequest request) throws JOSEException;

	public abstract ResponseEntity loginFacebook(Payload payload, HttpServletRequest request) throws JsonParseException,
			JsonMappingException, IOException, ParseException, JOSEException;

	public abstract ResponseEntity loginGoogle(Payload payload, HttpServletRequest request) throws JOSEException, ParseException,
			JsonParseException, JsonMappingException, IOException;

	public abstract ResponseEntity unlink(String provider, HttpServletRequest request) throws ParseException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException;
}
