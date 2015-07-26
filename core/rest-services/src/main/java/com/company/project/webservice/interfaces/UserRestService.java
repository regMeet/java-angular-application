package com.company.project.webservice.interfaces;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.persistence.entities.User;
import com.company.project.webservice.interfaces.base.BaseRestService;
import com.nimbusds.jose.JOSEException;

public interface UserRestService extends BaseRestService<User> {

	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<User> findByUserName(String name);

	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> findByEmail(String email);

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	ResponseEntity<User> getUser(HttpServletRequest request) throws ParseException, JOSEException, HttpAuthenticationException;

}
