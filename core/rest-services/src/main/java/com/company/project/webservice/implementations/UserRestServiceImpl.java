package com.company.project.webservice.implementations;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.Auth.AuthUtils;
import com.company.project.persistence.dao.implementations.exceptions.NonExistentEntityException;
import com.company.project.persistence.entities.Users;
import com.company.project.services.interfaces.UserService;
import com.company.project.webservice.implementations.base.BaseRestServiceImpl;
import com.company.project.webservice.interfaces.UserRestService;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/users")
public class UserRestServiceImpl extends BaseRestServiceImpl<Users, UserService> implements UserRestService {

	final static Logger log = Logger.getLogger(UserRestServiceImpl.class);

	@Autowired
	public UserRestServiceImpl(UserService baseService) {
		super(baseService);
	}

	// http://localhost:8089/web-services/users/hello
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "helloWorld";
	}

	@RequestMapping(value = "/displayMessage/{msg}", method = RequestMethod.GET)
	public @ResponseBody String displayMessage(@PathVariable String msg) {
		return "helloWorld " + msg;
	}

	@Override
	public Users findByUserName(String name) {
		return null;
	}

	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
	public @ResponseBody Users findById(@PathVariable("idUser") Integer idUser) {
		Optional<Users> user = baseService.findById(idUser);
		return user.get();
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public @ResponseBody Response getUser(HttpServletRequest request) throws ParseException, JOSEException {
		Optional<Users> foundUser = getAuthUser(request);

		if (!foundUser.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(foundUser.get()).build();
	}

	/*
	 * Helper methods
	 */
	private Optional<Users> getAuthUser(HttpServletRequest request) throws ParseException, JOSEException {
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		int idUser = Integer.parseInt(subject);
		Optional<Users> user = baseService.findById(idUser);
		return user;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleTodoNotFound(NonExistentEntityException ex) {

	}
}
