package com.company.project.webservice.implementations;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.persistence.dao.implementations.exceptions.NonexistentEntityException;
import com.company.project.persistence.entities.Users;
import com.company.project.services.interfaces.UserService;
import com.company.project.webservice.implementations.base.BaseRestServiceImpl;
import com.company.project.webservice.interfaces.UserRestService;
import com.google.common.base.Optional;

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

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Users> findAll() {
		List<Users> users = baseService.findAll();
		return users;
	}

//	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET, headers = "Accept=application/json")
	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
	public @ResponseBody Users findById(@PathVariable("idUser") Integer idUser) {
		Optional<Users> user = baseService.findById(idUser);
		return user.get();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody @Valid Users user) {
		baseService.create(user);
	}

	@RequestMapping(value = "/{idUser}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable("idUser") Integer idUser) {
		baseService.delete(idUser);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@RequestBody @Valid Users user) {
		baseService.update(user);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleTodoNotFound(NonexistentEntityException ex) {

	}
}
